use student
show dbs
db.dropDatabase()
db.pracownicy.insert({ id_prac : 100, "nazwisko" : "WEGLARZ", "placa_pod" : 1730 })
db.getCollectionNames() 
db.pracownicy.find()
db.pracownicy.insert({ id_prac : 100, "nazwisko" : "WEGLARZ", "placa_pod" : 1730 })
db.pracownicy.insert({ _id:100, id_prac : 100, "nazwisko" : "WEGLARZ", "placa_pod" :
1730 })
db.pracownicy.insert({ id_prac : 110, "nazwisko" : "BLAZEWICZ", "placa_pod" : 1350,
zatrudniony: new Date("1973-05-01") })
//zadanie 1
db.pracownicy.find()

db.pracownicy.drop()

db.pracownicy.insert([
{id_prac:100, nazwisko:"WEGLARZ", etat:'DYREKTOR', zatrudniony: new Date("1968-01-01"), placa_pod:1730.00, placa_dod:420.50, id_zesp:10},
{id_prac:110, nazwisko:'BLAZEWICZ', etat:"PROFESOR",id_szefa:100, zatrudniony: new Date("1973-05-01"), placa_pod:1350.00, placa_dod:210.00, id_zesp:40},
{id_prac:120, nazwisko:'SLOWINSKI', etat:"PROFESOR",id_szefa:100, zatrudniony: new Date("1977-09-01"), placa_pod:1070.00, id_zesp:30},
{id_prac:130, nazwisko:'BRZEZINSKI', etat:"PROFESOR",id_szefa:100, zatrudniony: new Date("1968-07-01"), placa_pod:960.00, id_zesp:20},
{id_prac:140, nazwisko:'MORZY', etat:"PROFESOR",id_szefa:130, zatrudniony: new Date("1975-09-15"), placa_pod:830.00, placa_dod:105.00, id_zesp:20},
{id_prac:150, nazwisko:'KROLIKOWSKI', etat:'ADIUNKT',id_szefa:130, zatrudniony: new Date("1977-09-01"), placa_pod:645.50, id_zesp:20},
{id_prac:160, nazwisko:'KOSZLAJDA', etat:'ADIUNKT', id_szefa:130, zatrudniony: new Date("1985-03-01"), placa_pod:590.00, id_zesp:20},
{id_prac:170, nazwisko:'JEZIERSKI', etat:'ASYSTENT', id_szefa:130, zatrudniony: new Date("1992-10-01"), placa_pod:439.70, placa_dod:80.50, id_zesp:20},
{id_prac:190, nazwisko:'MATYSIAK', etat:'ASYSTENT', id_szefa:140, zatrudniony: new Date("1993-09-01"), placa_pod:371.00, id_zesp:20},
{id_prac:180, nazwisko:'MAREK', etat:'SEKRETARKA', id_szefa:100, zatrudniony: new Date("1985-02-20"), placa_pod:410.20, id_zesp:10},
{id_prac:200, nazwisko:'ZAKRZEWICZ', etat:'STAZYSTA', id_szefa:140, zatrudniony: new Date("1994-07-15"), placa_pod:208.00, id_zesp:30},
{id_prac:210, nazwisko:'BIALY', etat:'STAZYSTA', id_szefa:130, zatrudniony: new Date("1993-10-15"), placa_pod:250.00, placa_dod:170.60, id_zesp:30},
{id_prac:220, nazwisko:'KONOPKA', etat:'ASYSTENT', id_szefa:110, zatrudniony: new Date("1993-10-01"), placa_pod:480.00, id_zesp:20},
{id_prac:230, nazwisko:'HAPKE', etat:'ASYSTENT', id_szefa:120, zatrudniony: new Date("1992-09-01"), placa_pod:480.00, placa_dod:90.00, id_zesp:30}])

//ZADANIE 2
db.zespoly.insert([{"id_zesp":10,"nazwa":"ADMINISTRACJA","adres":"PIOTROWO 3A"},
{"id_zesp":20,"nazwa":"SYSTEMY ROZPROSZONE","adres":"PIOTROWO 3A"},
{"id_zesp":30,"nazwa":"SYSTEMY EKSPERCKIE","adres":"STRZELECKA 14"},
{"id_zesp":40,"nazwa":"ALGORYTMY","adres":"WLODKOWICA 16"},
{"id_zesp":50,"nazwa":"BADANIA OPERACYJNE","adres":"MIELZYNSKIEGO 30"}])
db.zespoly.find()

db.pracownicy.find({"etat":"PROFESOR"})
db.pracownicy.find({"etat":"PROFESOR"}).pretty()

db.pracownicy.find({"etat":"PROFESOR"}, {"nazwisko":1})
db.pracownicy.find({"etat":"PROFESOR"}, {"nazwisko":1, "placa_pod":1})
//ZADANIE 3
db.pracownicy.find({"etat":"PROFESOR"}, {"nazwisko":1, "_id":0}) //tylko nazwisko
db.pracownicy.find({"etat":"PROFESOR"}, {"nazwisko":0, "_id":0}) //wszystko bez nazwisk i ID

db.pracownicy.find({"etat":{$ne:"PROFESOR"}})
db.pracownicy.find(
{"placa_pod":{$gt:500}},
{"nazwisko":1,"placa_pod":1}
) 
//and
db.pracownicy.find(
{"placa_pod":{$gt:1000}, "etat":"PROFESOR"},
{"nazwisko":1,"placa_pod":1}
)
//and - jawnie
db.pracownicy.find(
{$and:[{"placa_pod":{$gt:1000}}, {"etat":"PROFESOR"}]},
{"nazwisko":1,"placa_pod":1}
)
// te same pola - and jawny
db.pracownicy.find(
{$and:[{"placa_pod":{$gt:500}}, {"placa_pod":{$lt:700}}]},
{"nazwisko":1,"placa_pod":1}
)
//najkrotsze
db.pracownicy.find(
{"placa_pod":{$gt:500, $lt:700}},
{"nazwisko":1,"placa_pod":1}
) 
//ZADANIE 4
db.pracownicy.find(
{$or:[{"placa_pod":{$gt:200, $lt:500}},{"etat":"ASYSTENT"}]},
{"_id":0,"nazwisko":1,"etat":1,"placa_pod":1}
)
//ZADANIE 5
db.pracownicy.find(
{"placa_pod":{$gt:400}},
{"nazwisko":1,"etat":1, "placa_pod":1}
).sort(
{"etat":1,"placa_pod":-1})

//ZADANIE 6
db.pracownicy.find(
{"id_zesp":20},
{"nazwisko":1, "placa_pod":1}
).sort(
{"placa_pod":-1}
).limit(1).skip(1)

//ZADANIE 7
db.pracownicy.find(
{ "id_zesp":{$in:[20,30]},"etat":{$ne:"ASYSTENT"},"nazwisko":{$regex:/I$/}},
{"nazwisko":1, "etat":1}
)

//ZADANIE 8
db.pracownicy.aggregate([
    {$project: {
       "_id": 0,
       "stanowisko": "$etat",
       "nazwisko": 1,
       "rok_zatrudnienia": {$year: "$zatrudniony"} 
    }},
    {$sort: {"placa_pod": -1}},
    {$skip: 2},
    {$limit: 1},
])
    
//ZADANIE 9
db.pracownicy.aggregate([
    {$group: {
        "_id": "$id_zesp",
        "liczba": {$sum: 1},
    }},
    {$match: {
        "liczba": {$gt: 3},
    }},
])
    
//ZADANIE 10
db.pracownicy.aggregate([
    {$lookup: {
        from: "zespoly",
        localField: "id_zesp",
        foreignField: "id_zesp",
        as: "dept",
    }},
    {$match: {
        "id_zesp": {$in: [20, 30]},
    }},
    {$project: {
        "_id": 0,
        "nazwisko": 1,
        "dept": {$arrayElemAt: ["$dept.adres", 0]},
    }},
])

//ZADANIE 11
db.pracownicy.aggregate([
    {$lookup: {
        from: "zespoly",
        localField: "id_zesp",
        foreignField: "id_zesp",
        as: "dept",
    }},
    {$match: {
        "dept.adres": {$regex: /STRZELECKA/},
    }},
    {$project: {
        "_id": 0,
        "nazwisko": 1,
        "dept": {$arrayElemAt: ["$dept.nazwa", 0]},
    }},
])
    
//ZADANIE 12
var pracownicy = db.pracownicy.find();
while (pracownicy.hasNext()) {
 prac = pracownicy.next();
 zesp = db.zespoly.findOne({"id_zesp": prac.id_zesp});
 db.pracownicy.update(
    {"_id": prac._id},
    {$set: {"id_zesp": zesp._id}}
 );
}
//###############################################
//EMBEDDED
//###############################################

db.produkty.insert(
[{
 nazwa: "Kosiarka spalinowa",
 cena: 1000,
 cechy: {
 zbiornik_paliwa_pojemnosc: 0.8,
 waga: 23
 },
 tagi: ["maszyna", "ogrod", "dom", "kosiarka"],
 oceny:
 [ {osoba: "Jurek", ocena: 3},
 {osoba: "Ania", ocena: 4},
 {osoba: "Basia", ocena: 3.6}
 ]
},
{ 
 nazwa: "Wiertarka udarowa",
 cena: 1200,
 cechy: {
 moc_udaru: 4,
 maksymalne_obroty: 4000,
 uchwyt: "SDS"
 },
 tagi: ["wiertarka"],
 oceny:
 [ {osoba: "Michał", ocena: 5},
 {osoba: "Roman", ocena: 4.8},
 ]
},
{
 nazwa: "Wiertarko - wkrętarka",
 cena: 450,
 cechy: {
 pojemnosc_akumulatora: 1.3,
 czas_ladowania: 60
 },
 tagi: ["wiertarka", "dom"],
 oceny:
 [ {osoba: "Ania", ocena: 5},
 {osoba: "Robert", ocena: 4},
 {osoba: "Janusz", ocena: 4},
 {osoba: "Julita", ocena: 3}
 ]
},
{
 nazwa: "Kosiarka elektryczna",
 cena: 900,
 cechy: {
 moc: 1700,
 waga: 17
 },
 tagi: ["kosiarka", "ogrod", "dom"],
 oceny:
 [ {osoba: "Monika", ocena: 3},
 {osoba: "Karol", ocena: 4}
 ]
}
])
db.produkty.find()

//ZADANIE 13
db.produkty.find(
    {"oceny.osoba": {"$nin":["Ania","Karol"]}},
    {"_id":0, "nazwa": 1}
)
    
//ZADANIE 14
db.produkty.aggregate([
 { $unwind : "$oceny" },
 {$group: {
    _id:"$_id",
    srednia_ocena: {$avg: "$oceny.ocena"}
 }},
 {$sort: {srednia_ocena: -1}},
 {$limit: 1},]
)
 
//ZADANIE 15
db.produkty.update(
 {"nazwa":"Kosiarka spalinowa"},
 {$push: {osoba: "Jack", ocena: 4}}
)
 
//ZADANIE 16
db.produkty.find()
 
var produkty = db.produkty.find();
while (produkty.hasNext()) {
 prod = produkty.next();
 db.produkty.update(
    {"nazwa":prod.nazwa},
 { $pull: { "oceny": { "ocena": { $lte: 4} } } }
)
}
 
db.produkty.drop()