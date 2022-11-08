from kogpt2.chatbot import *
from flask import Flask, request
import json

app = Flask(__name__)

chatbot = Chatbot()

@app.route('/request/chatting', methods=['POST'])
def on_request_buses():
    ret = {'success': True}
    try:
        data = json.loads(request.data.decode('utf-8'))
        ret['data'] = chatbot.predict(data['chat'])
    except Exception as e:
        ret['success'] = False
        ret['error'] = str(e)
    finally:
        return json.dumps(ret)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3004)
 
