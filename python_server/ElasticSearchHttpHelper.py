'''
如果搜researcher：只模糊匹配name字段，返回id list
如果搜topic：模糊匹配EXPERTISE、homepage字段，返回id list
DB中存储name, author_id, EXPERTISE, homepage
'''
import json
import os
import re


class ElasticSearchHttpHelper:
    IP = "http://10.240.118.159:9200/"
    name_set = set()
    stopwords_list = ['`', '\'', '.']
    return_value = []

    def ElasticSearchHttpHelper(self):
        self.name_set = set()

    # ok
    def http_get_index_info(self, index_name, type_name, id_name):
        # curl -X GET "localhost:9200/customer/_doc/1?pretty"
        cmd = "curl -X GET " + self.IP + "/" + index_name + "/" + type_name + "/" + id_name + "?pretty"
        print('[cmd]: ' + cmd)
        print(os.popen(cmd).readlines())


    '''
    index_name: 默认为chengdu80
    '''
    def get_researcher(self, is_topic, is_researcher, query, index_name):
        if is_researcher:
            line = self.match_name(query, index_name)
            print("line = %s" % (line))
            line_len = len(line)
            new_line = ""
            for i in range(line_len):
                new_line = new_line + line[i]
            my_list = re.findall(r"\"_id\" : \"(.+?)\"", new_line)
            print(my_list)
            return my_list
        else:
            line = self.match_topic(query, index_name)
            print("line = %s" % (line))
            line_len = len(line)
            new_line = ""
            for i in range(line_len):
                new_line = new_line + line[i]
            my_list = re.findall(r"\"_id\" : \"(.+?)\"", new_line)
            print(my_list)
            return my_list


    def match_topic(self, query, index_name):
        params = '{"query": {"bool": {"should": [{"match": {"name": {"query": "' + query + '","boost": 2 }}},{"match": {"EXPERTISE": {"query": "'+ query + '","boost": 10 }}},{"match": {"homepage": {"query": "' + query + '","boost": 2 }}}]}},"_source":["author_id"],"size": 20}'
        cmd = 'curl -H Content-Type:application/json -XGET \'' + self.IP + index_name + '/_search?pretty\'' + ' -d \'' + params + '\''
        print(cmd)
        return os.popen(cmd).readlines()


    def match_name(self, query, index_name):
        # {"query": {"bool": {"should": [{"match": {"name": {"query": "str","boost": 2 }}}]}}}
        # curl -X GET "localhost:9200/trial/doc" -H 'Content-Type: application/json' -d
        params = '{"query": {"bool": {"should": [{"match": {"name": {"query": "' + query + '","boost": 100 }}}]}},"_source":["author_id"],"size": 20}'
        cmd = 'curl -H Content-Type:application/json -XGET \'' + self.IP + index_name + '/_search?pretty\'' + ' -d \'' + params + '\''
        print(cmd)
        return os.popen(cmd).readlines()


    # params是一个json的string
    def http_put(self, index_name, type_name, id_name, params):
        cmd = 'curl -H Content-Type:application/json -X PUT' + self.IP + index_name + '/' + type_name + '/' + id_name + '/'+ '?pretty' + ' -d \'' + params + '\''
        # print('[cmd]: ' + cmd)
        os.system(cmd)
        # os.popen(cmd)
        # print(os.popen(cmd).readlines())

    def String_filter(self, str_value):
        filter_list = ["webpage_url", "image_url", "anchor_text", "msa_papers"]
        json_map = json.loads(str_value)
        for item in filter_list:
            json_map.pop(item)
        return json.dumps(json_map)


    def PUT_file(self, index_name, type_name, filename):
        file = open(filename, 'r', encoding='utf-8')
        line = file.readline()
        cnt = 0
        while(line is not None):
            for sign in self.stopwords_list:
                line = line.replace(sign, ' ')
            line = self.String_filter(line)
            line_json = json.loads(line)
            name = line_json['name']
            author_id = line_json['author_id']
            # id为空或名字集合已经存在该名字
            if author_id == 0 or name in self.name_set:
                line = file.readline()
                continue
            self.http_put(index_name, type_name, str(author_id), line)
            self.name_set.add(name)
            cnt += 1
            print(cnt)
            line = file.readline()
        file.close()

    # # params是个map
    # def urllib_put(self, url, params):
    #     data = urllib.parse.urlencode(params)
    #     data = data.encode('utf-8')
    #     req = urllib.request.Request(url=url, data=data, method='PUT')
    #     req.add_header('Content-Type', 'application/json')
    #     response = urllib.request.urlopen(url)  # 发送页面请求
    #     return response.read()


    def http_delete(self, index_name, type_name):
        # curl -X DELETE "localhost:9200/customer?pretty"
        cmd = "curl -X DELETE " + self.IP + "/" + index_name + "/" + type_name + "?pretty"
        print('[cmd]: ' + cmd)
        print(os.popen(cmd).readlines())


def trial():
    str = '"temple_university`Explore Temple\'s Schools and Colleges`Learn more'


if __name__ == "__main__":

    filename = "data/out_out_faculty_members_part2_with_homepage.json"
    manager = ElasticSearchHttpHelper()
    # manager.PUT_file("chengdu80", "researcher", filename)     # upload data
    # manager.http_delete("chengdu80", "")      # delete data
    manager.get_researcher(False, True, "Yan Yan", "chengdu80")    
    # manager.http_get_index_info("chengdu80", "researcher", str(2654673094))

    # str_value = '{"webpage_url": "1", "image_url": "2", "anchor_text": "3", "EXPERTISE": ["clinical", "psychology", "physical"], "msa_papers": 4}'
    # print(manager.String_filter(str_value))






