from flask import Flask, request
import json
import urllib.parse
import ElasticSearchHttpHelper

app = Flask(__name__)
 
@app.route('/')
def hello_world():
    return 'hello world'
 
@app.route('/query', methods=['GET','POST'])
def query():
    raw = request.get_data()
    print(raw)
    data = json.loads(str(raw, encoding = "utf-8"))
    print(data["content"])
    return json.dumps(elastic_search(data["content"], data["isName"]))
 
def elastic_search(content, is_researcher):
    manager = ElasticSearchHttpHelper.ElasticSearchHttpHelper()
    out = manager.get_researcher(not is_researcher, is_researcher, content, "chengdu80")
    out = list(map(lambda s: int(s), out))
    return out

if __name__ == '__main__':
    app.run(debug=True)