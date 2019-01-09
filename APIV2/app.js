//load our app server usng express somehow ..
const express = require('express')
const app = express()
const morgan = require('morgan')
//const mysql = require('mysql')
const mysql2 = require('sync-mysql')

 //connection mysql
 /*const connection = mysql2.createConnection({
  host: 'localhost',
  user: 'root',
  password:"",
  database: 'PlanteMe'
})*/
let connection = new mysql2({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'PlanteMe'
});



app.use(morgan('combined'))


app.get("/", (req,res) => {
  console.log("Responding to root route")
  res.send("Hello from roooot")
})

app.get("/plante",(req,res) => {

  
  let idTypeParam 
  let idFamilleParam 
  const queryString = "select * from plante "
  const queryType = "select * from type where id = ?"
  const queryFamille = "select * from famille where id = ?"
  let idType 
  let nomType
  let idFamille 
  let nomFamille
  let resultPlant = connection.query(queryString) 
    if(resultPlant.length > 0){
      let plante = resultPlant.map((row) => {
        return {id:row.id, nomFr:row.nomFr, nomLatin:row.nomLatin,
          couleurFleurs:row.couleurFleurs, exposition:row.exposition,
          sol:row.sol, usageMilieu:row.usageMilieu,type : {},famille : {}
        }
      })
     for(let i=0;i<resultPlant.length;i++){
       
      idTypeParam = resultPlant[i].idType
      idFamilleParam = resultPlant[i].idFamille
  
    //type
    let resultType = connection. query(queryType,[idTypeParam])
      idType = resultType[0].id
      nomType = resultType[0].nom
    //famille
    let resultFamille = connection. query(queryFamille,[idFamilleParam])
      idFamille = resultFamille[0].id
      nomFamille = resultFamille[0].nom

  //affectation des valeurs
  
  plante[i].type = {id:idType,nom:nomType}
  plante[i].famille = {id:idFamille,nom:nomFamille}
  //console.log(resultPlant)
     }
      
    res.json(plante)
    } else {
      res.sendStatus(204)
      return
    }  
})

app.get("/plante",(req,res) => {

  
  let idTypeParam 
  let idFamilleParam 
  const queryString = "select * from plante "
  const queryType = "select * from type where id = ?"
  const queryFamille = "select * from famille where id = ?"
  let idType 
  let nomType
  let idFamille 
  let nomFamille
  let resultPlant = connection.query(queryString) 
    if(resultPlant.length > 0){
      let plante = resultPlant.map((row) => {
        return {id:row.id, nomFr:row.nomFr, nomLatin:row.nomLatin,
          couleurFleurs:row.couleurFleurs, exposition:row.exposition,
          sol:row.sol, usageMilieu:row.usageMilieu,type : {},famille : {}
        }
      })
     for(let i=0;i<resultPlant.length;i++){
       
      idTypeParam = resultPlant[i].idType
      idFamilleParam = resultPlant[i].idFamille
  
    //type
    let resultType = connection. query(queryType,[idTypeParam])
      idType = resultType[0].id
      nomType = resultType[0].nom
    //famille
    let resultFamille = connection. query(queryFamille,[idFamilleParam])
      idFamille = resultFamille[0].id
      nomFamille = resultFamille[0].nom

  //affectation des valeurs
  
  plante[i].type = {id:idType,nom:nomType}
  plante[i].famille = {id:idFamille,nom:nomFamille}
  //console.log(resultPlant)
     }
      
    res.json(plante)
    } else {
      res.sendStatus(204)
      return
    }  
})

app.get("/plante/:id",(req,res) => {
  
 

  const planteId = req.params.id
  let idTypeParam 
  let idFamilleParam 
  const queryString = "select * from plante where id = ?"
  const queryType = "select * from type where id = ?"
  const queryFamille = "select * from famille where id = ?"
  let idType 
  let nomType
  let idFamille 
  let nomFamille
  let resultPlant = connection.query(queryString,[planteId]) 
    if(resultPlant.length > 0){
      let plante = resultPlant.map((row) => {
        return {id:row.id, nomFr:row.nomFr, nomLatin:row.nomLatin,
          couleurFleurs:row.couleurFleurs, exposition:row.exposition,
          sol:row.sol, usageMilieu:row.usageMilieu,type : {},famille : {}
        }
      })
     
      idTypeParam = resultPlant[0].idType   
      idFamilleParam = resultPlant[0].idFamille   
  
    //type
    let resultType = connection. query(queryType,[idTypeParam])
      idType = resultType[0].id
      nomType = resultType[0].nom
    //famille
    let resultFamille = connection. query(queryFamille,[idFamilleParam])
      idFamille = resultFamille[0].id
      nomFamille = resultFamille[0].nom
  
      //affectation des valeurs
      plante[0].type = {id:idType,nom:nomType}
      plante[0].famille = {id:idFamille,nom:nomFamille}
      console.log(resultPlant)
    res.json(plante)
    } else {
      res.sendStatus(404)
      return
    }
    
})

//localhost:3003
app.listen(3000, () => {
  console.log("Server is up and listening on 3000 ...")
})

