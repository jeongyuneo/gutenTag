import numpy as np
import glob
from six import BytesIO
from PIL import Image
import tensorflow as tf
from object_detection.utils import ops as utils_ops
from object_detection.utils import label_map_util
from object_detection.utils import visualization_utils as vis_util
from flask import Flask, json
import time

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False

global image_path
top = ['후드', '맨투맨/니트', '셔츠']
bottom = ['코튼팬츠', '데님팬츠', '슬랙스', '트레이닝/조거팬츠']
outer = ['블레이저', '가디건', '코트', '플리스/뽀글이', '후드집업', '가죽/무스탕', '롱패딩', '숏패딩']
shoes = ['부츠/구두', '운동화', '스니커즈']
hat = ['비니', '버킷햇', '캡']
style = ['캐주얼', '포멀', '스트릿']
category = [top, bottom, outer, shoes, hat]
result = {}

def load_image_into_numpy_array(path):
    img_data = tf.io.gfile.GFile(path, 'rb').read()
    image = Image.open(BytesIO(img_data))
    (im_width, im_height) = image.size
    return np.array(image.getdata()).reshape(
        (im_height, im_width, 3)).astype(np.uint8)

def run_inference_for_single_image(model, image):
    image = np.asarray(image)
    input_tensor = tf.convert_to_tensor(image)
    input_tensor = input_tensor[tf.newaxis, ...]

    model_fn = model.signatures['serving_default']
    output_dict = model_fn(input_tensor)

    num_detections = int(output_dict.pop('num_detections'))
    output_dict = {key: value[0, :num_detections].numpy()
                   for key, value in output_dict.items()}
    output_dict['num_detections'] = num_detections
    output_dict['detection_classes'] = output_dict['detection_classes'].astype(np.int64)

    if 'detection_masks' in output_dict:
        detection_masks_reframed = utils_ops.reframe_box_masks_to_image_masks(
            output_dict['detection_masks'], output_dict['detection_boxes'],
            image.shape[0], image.shape[1])
        detection_masks_reframed = tf.cast(detection_masks_reframed > 0.5,
                                           tf.uint8)
        output_dict['detection_masks_reframed'] = detection_masks_reframed.numpy()
    return output_dict

def get_class_id(output_dict):
    class_number = output_dict['detection_classes'][0]
    score = output_dict['detection_scores'][0]
    if score <= 0.4:
        return None
    return class_number

def create_graph():
    with tf.compat.v1.gfile.FastGFile(model_style, 'rb') as f:
        graph_def = tf.compat.v1.GraphDef()
        graph_def.ParseFromString(f.read())
        _ = tf.import_graph_def(graph_def, name='')

@app.route('/closet/request')
def requestResult():
    image_path = '/Users/yurr/IdeaProjects/gutenTag/src/main/resources/Image/Closet/image.jpg'

    # 상품 스타일
    answer = None
    if not tf.compat.v1.gfile.Exists(image_path):
        tf.compat.v1.logging.fatal('File does not exist %s', image_path)
        return answer
    image_data = tf.compat.v1.gfile.FastGFile(image_path, 'rb').read()
    create_graph()
    with tf.compat.v1.Session() as sess:
        softmax_tensor = sess.graph.get_tensor_by_name('final_result:0')
        predictions = sess.run(softmax_tensor,
                               {'DecodeJpeg/contents:0': image_data})
        predictions = np.squeeze(predictions)

        top_k = predictions.argsort()[-5:][::-1]  # 가장 높은 확률을 가진 5개(top 5)의 예측값(predictions)을 얻는다.
        f = open(label_style, 'rb')
        lines = f.readlines()
        labels = [str(w).replace("\n", "") for w in lines]

        max_label = labels[top_k[0]]
        max_label = max_label.split('\\')[0]
        max_label = max_label.split('\'')[1]
        if max_label == 'casual':
            max_label = '캐주얼'
        elif max_label == 'formal':
            max_label = '포멀'
        else:
            max_label = '스트릿'
        result['style'] = max_label

    # 상품 태그
    tmp = []
    for i in range(5):
        category_index = label_map_util.create_category_index_from_labelmap(labelmap_path[i], use_display_name=True)
        for image_path in glob.glob(image_path):
            image_np = load_image_into_numpy_array(image_path)
            output_dict = run_inference_for_single_image(model[i], image_np)
            vis_util.visualize_boxes_and_labels_on_image_array(
                image_np,
                output_dict['detection_boxes'],
                output_dict['detection_classes'],
                output_dict['detection_scores'],
                category_index,
                instance_masks=output_dict.get('detection_masks_reframed', None),
                use_normalized_coordinates=True,
                line_thickness=8)
        if get_class_id(output_dict) != None:
            tmp.append(category[i][get_class_id(output_dict)-1])
    result['category'] = tmp
    return json.dumps(result)

start = time.time()

labelmap_path_top = '/Users/yurr/PycharmProjects/gutenTag/models/labelmap-top.pbtxt'
labelmap_path_bottom = '/Users/yurr/PycharmProjects/gutenTag/models/labelmap-bottom.pbtxt'
labelmap_path_outer = '/Users/yurr/PycharmProjects/gutenTag/models/labelmap-outer.pbtxt'
labelmap_path_shoes = '/Users/yurr/PycharmProjects/gutenTag/models/labelmap-shoes.pbtxt'
labelmap_path_hat = '/Users/yurr/PycharmProjects/gutenTag/models/labelmap-hat.pbtxt'
label_style = '/Users/yurr/PycharmProjects/gutenTag/models/label_style.txt'
labelmap_path = [labelmap_path_top, labelmap_path_bottom, labelmap_path_outer, labelmap_path_shoes, labelmap_path_hat]

tf.keras.backend.clear_session()
model_top = tf.saved_model.load('/Users/yurr/PycharmProjects/gutenTag/models/top/saved_model')
model_bottom = tf.saved_model.load('/Users/yurr/PycharmProjects/gutenTag/models/bottom/saved_model')
model_outer = tf.saved_model.load('/Users/yurr/PycharmProjects/gutenTag/models/outer/saved_model')
model_shoes = tf.saved_model.load('/Users/yurr/PycharmProjects/gutenTag/models/shoes/saved_model')
model_hat = tf.saved_model.load('/Users/yurr/PycharmProjects/gutenTag/models/hat/saved_model')
model_style = '/Users/yurr/PycharmProjects/gutenTag/models/style/output_graph.pb'
model = [model_top, model_bottom, model_outer, model_shoes, model_hat]

end = time.time() - start

print('load success')
print('load time :', end)

if __name__ == '__main__':
    app.run()