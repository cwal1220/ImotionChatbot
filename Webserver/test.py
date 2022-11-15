from requester import db_requester
import time

# db = db_requester('http://49.236.136.179:5000')
# print(db.request('request/car', {'id': 'cshyeon'}))

db = db_requester('http://138.2.126.137:3005')
print(db.request('/request/chatting', {'chat':'아 자살하고싶다..'}))

#print(db.request('/update/reservations', {'res_id':'123123_99', 'bus_id':"99", 'std_id':'123123'}))
# print(db.request('/update/needs', {'bus_id':99}))
# print(db.request('/request/bustime_by_busid', {'bus_id':99}))
# print(db.request('/request/busid_by_stdid', {'std_id':'7469'}))


#print(db.request('/update/reservations', {'res_id':'69697474_170', 'bus_id':170, 'std_id':'69697474_170'}))
#print(db.request('/request/reservations_by_busid',{'bus_id':99}))

#print(db.request('/request/buses', {'start_datetime':'2022-10-31 7:00:00', 'end_datetime':'2022-10-31 8:20:00'}))
# print(db.request('/update/reservations', {'res_id':'2012150022_5', 'bus_id':5, 'std_id':'2012150022'}))
# print(db.request('/update/reservations', {'res_id':'2015150022_5', 'bus_id':10, 'std_id':'2015150022'}))
# print(db.request('/update/needs', {'bus_id':2}))
# print(db.request('/update/reservations', {'res_id':'123456789_99', 'bus_id':99, 'std_id':'123456789'}))
# time.sleep(1)
# print(db.request('/update/reservations', {'res_id':'7469_170', 'bus_id':170, 'std_id':'7469'}))
# time.sleep(1)
# print(db.request('/update/reservations', {'res_id':'7474_170', 'bus_id':170, 'std_id':'7474'}))
# time.sleep(1)
# print(db.request('/update/reservations', {'res_id':'6969_170', 'bus_id':170, 'std_id':'6969'}))
# time.sleep(1)
# print(db.request('/request/bustime_by_busid', {'bus_id':170}))
# print(db.request('/request/busid_by_stdid', {'std_id':'7469'}))
# print(db.request('/request/reservations_by_busid', {'bus_id':170}))

# a = Chatbot()
# print(a.predict("아 자살하고싶다."))