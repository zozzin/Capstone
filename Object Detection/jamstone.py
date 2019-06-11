import os
import cv2
import numpy as np
import tensorflow as tf
import pymysql
import time

camera = cv2.VideoCapture(0)

connect = pymysql.connect(host='wlsdn9721.cafe24.com', user='wlsdn9721',
		password='q1w2e3r4!', database='wlsdn9721')

label_lines = [line.rstrip() for line
               in tf.gfile.GFile('output_labels.txt')]

def grabVideoFeed():
    grabbed, frame = camera.read()
    return frame if grabbed else None
    
def initialSetup():
    os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
    
    with tf.gfile.FastGFile('output_graph.pb', 'rb') as f:
        graph_def = tf.GraphDef()
        graph_def.ParseFromString(f.read())
        tf.import_graph_def(graph_def, name='')

initialSetup()

with tf.Session() as sess:
    
    softmax_tensor = sess.graph.get_tensor_by_name('final_result:0')
    
    curs = connect.cursor()
    vegok = 1
    appleok = 1
    carrotok = 1
    containerok = 1
    milkok = 1
    
    while True:
        
        frame = grabVideoFeed()
        
        if frame is None:
            raise SystemError('Issue grabbing the frame')
            
        frame = cv2.resize(frame, (299, 299), interpolation=cv2.INTER_CUBIC)
        cv2.imshow('Main', frame)
        
        numpy_frame = np.asarray(frame)
        numpy_frame = cv2.normalize(numpy_frame.astype('float'), None, -0.5, .5, cv2.NORM_MINMAX)
        numpy_final = np.expand_dims(numpy_frame, axis=0)
        
        predictions = sess.run(softmax_tensor, {'Mul:0': numpy_final})
        
        top_k = predictions[0].argsort()[-len(predictions[0]):][::-1]
        
        for node_id in top_k:
            human_string = label_lines[node_id]
            score = predictions[0][node_id]
            print('%s (score = %.5f)' % (human_string, score))
            
            if human_string == 'veg' and score >= 0.97 and vegok == 1:
                cv2.imwrite('veg.jpg', frame)
                os.system("mpg123 /home/pi/veg.mp3")
                sql = "insert into Food(Name) values(%s)"
                curs.execute(sql, ('cabbage'))
                connect.commit()
                vegok = 0
                appleok = 1
                carrotok = 1
                containerok = 1
                milkok = 1
            elif human_string == 'apple' and score >= 0.9999 and appleok == 1:
                cv2.imwrite('apple.jpg', frame)
                os.system("mpg123 /home/pi/apple.mp3")
                sql = "insert into Food(Name) values(%s)"
                curs.execute(sql, ('apple'))
                connect.commit()
                appleok = 0
                vegok = 1
                carrotok = 1
                containerok = 1
                milkok = 1
            elif human_string == 'carrot' and score >= 0.999  and carrotok == 1:
                cv2.imwrite('carrot.jpg', frame)
                os.system("mpg123 /home/pi/carrot.mp3")
                sql = "insert into Food(Name) values(%s)"
                curs.execute(sql, ('carrot'))
                connect.commit()
                carrotok = 0
                vegok = 1
                appleok = 1
                containerok = 1
                milkok = 1
            elif human_string == 'container' and score >= 0.985 and containerok == 1:
                cv2.imwrite('container.jpg', frame)
                os.system("mpg123 /home/pi/container.mp3")
                sql = "insert into Food(Name) values(%s)"
                curs.execute(sql, ('container'))
                connect.commit()
                containerok = 0
                vegok = 1
                appleok = 1
                carrotok = 1
                milkok = 1
            elif human_string == 'milk' and score >= 0.985 and milkok == 1:
                cv2.imwrite('milk.jpg', frame)
                os.system("mpg123 /home/pi/milk.mp3")
                sql = "insert into Food(Name) values(%s)"
                curs.execute(sql, ('milk'))
                connect.commit()
                milkok = 0
                containerok = 1
                vegok = 1
                appleok = 1
                carrotok = 1
            else:
                pass
        
        print ('********* Session Ended *********')
        
        if cv2.waitKey(1) & 0xFF == ord('q'):
            connect.close()
            sess.close()
            break

camera.release()
cv2.destroyAllWindows()
