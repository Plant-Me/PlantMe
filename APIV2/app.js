//load our app server usng express somehow ..
const express = require('express')
const app = express()
const morgan = require('morgan')
const mysqlAsync = require('mysql')
const mysqlSync= require('sync-mysql')

 //connection mysql
 const connectionAsync = mysqlAsync.createConnection({
  host: 'localhost',
  user: 'root',
  password:"",
  database: 'PlanteMe'
})
const connectionSync = new mysqlSync({
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

  
  const queryString = "SELECT idPlante,id_image,nomFr,url, usageMilieu from plante inner JOIN image on plante.id_image = image.idImage "
  connectionAsync.query(queryString,(err,rows,fields)=> {
    if (err){
      console.log("error " + err)
      res.sendStatus(204)
      return
    }
    const plante = rows.map((row) => {
      return {idPlante:row.id, nomFr:row.nomFr, usageMilieu:row.usageMilieu,
        image :{idImage:row.id_image,url:row.url}}
    })
    res.json(plante)
  }) 
    
    
    
})

app.get("/plante/:id",(req,res) => {
  
 

  const planteId = req.params.id
  const queryString = "SELECT idPlante,nomFr,nomLatin,description,couleurFleurs,exposition,sol,usageMilieu,famille.idFamille,famille.nom,image.idImage,image.url,type.idType,type.nom from plante "
   + "inner JOIN famille on plante.id_famille = famille.idFamille INNER JOIN image on plante.id_image = image.idImage INNER join type on plante.id_type = type.idType where plante.idPlante = ?"
  connectionAsync.query(queryString,[planteId] ,(err,rows,fields) => {
    if (err){
      console.log("error " + err)
      res.sendStatus(204)
      return
    }
    const plante = rows.map((row) => {
      return {idPlante:row.idPlante, nomFr:row.nomFr, nomLatin:row.nomLatin,description:row.description,
        couleurFleurs:row.couleurFleurs, exposition:row.exposition,
        sol:row.sol, usageMilieu:row.usageMilieu,type : {idType:row.idType,nom:row.nom},image : {idImage:row.idImage,url:row.url},
        famille : {idFamille:row.idFamille,nom:row.nom}
      }
    })
    res.json(plante)
  })
})

//localhost:3003
app.listen(3000, () => {
  console.log("Server is up and listening on 3000 ...")
})

