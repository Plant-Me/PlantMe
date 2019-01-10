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
  const queryString = "SELECT idPlante,nomFr,nomLatin,description,couleurFleurs,exposition,sol,usageMilieu,famille.idFamille,famille.nom as nomFamille,image.idImage,image.url,type.idType,type.nom from plante inner JOIN famille on plante.id_famille = famille.idFamille INNER JOIN image on plante.id_image = image.idImage INNER join type on plante.id_type = type.idType where plante.idPlante = ?"
  const queryString2 = "SELECT * FROM plante_calendrier inner join action_calendrier on action_calendrier.idActionCalendrier = plante_calendrier.id_action_calendrier inner join mois on mois.idMois = plante_calendrier.id_mois WHERE plante_calendrier.id_plante = ?"
  
  let resultquery1 = connectionSync.query(queryString,[planteId])
    if (resultquery1.length > 0){
      let plante = resultquery1.map((row) => {
        return {idPlante:row.idPlante, nomFr:row.nomFr, nomLatin:row.nomLatin,description:row.description,
          couleurFleurs:row.couleurFleurs, exposition:row.exposition,
          sol:row.sol, usageMilieu:row.usageMilieu,type : {idType:row.idType,nom:row.nom},image : {idImage:row.idImage,url:row.url},
          famille : {idFamille:row.idFamille,nom:row.nomFamille}, 
          actions : []
      
        }
      })
        let resultquery2 = connectionSync.query(queryString2,[planteId])
        if (resultquery1.lenght > 0){
          res.sendStatus(404)
          return
        }else {
          for(let i=0;i<resultquery2.length;i++){

            plante[0].actions[i] = {
              idActionCalendrier : resultquery2[i].idActionCalendrier,
              idType : resultquery2[i].type,
              idMois : resultquery2[i].idMois,
              mois : resultquery2[i].nom      
            }      
          }
        }
        res.json(plante)
      }else {
      res.sendStatus(404)
      return
  }
    
  })

app.get("/utilisateur/:idUtilisateur/plante",(req,res) => {

  const utilisateurId = req.params.idUtilisateur
  const queryString = "select plante.idPlante,plante.id_image,image.url,plante.id_type,type.nom,plante.usageMilieu,plante.nomFr,plantes_utilisateur.id_plante_utilisateur,plantes_utilisateur.nom_personnel "
  + "from plante inner join image on plante.id_image = image.idImage INNER join plantes_utilisateur on plante.idPlante = plantes_utilisateur.id_plante INNER join type on plante.id_type = type.idType where plantes_utilisateur.id_utilisateur = ? "
  connectionAsync.query(queryString,[utilisateurId],(err,rows,fields)=> {
    if (err){
      console.log("error " + err)
      res.sendStatus(404)
      return
    }
    const plante = rows.map((row) => {
      return {idPlante:row.idPlante,idPlanteUtilisateur:row.id_plante_utilisateur, nomFr:row.nomFr,nomPersonnel:row.nom_personnel, usageMilieu:row.usageMilieu,
        image :{idImage:row.id_image,url:row.url},type:{idType:row.id_type,nom:row.nom}}
    })
    res.json(plante)
  })   
})

app.get("/utilisateur/:idUtilisateur/plante/:idPlanteUtilisateur",(req,res) => {

  const utilisateurId = req.params.idUtilisateur
  const idPlanteUtilisateur = req.params.idPlanteUtilisateur

  const queryString = "select plante.idPlante,plante.id_image,image.url,plante.id_type,type.nom,plante.usageMilieu,plante.nomFr,plantes_utilisateur.id_plante_utilisateur,plantes_utilisateur.nom_personnel "
  + "from plante inner join image on plante.id_image = image.idImage INNER join plantes_utilisateur on plante.idPlante = plantes_utilisateur.id_plante INNER join type on plante.id_type = type.idType where plantes_utilisateur.id_utilisateur = ? "
  connectionAsync.query(queryString,[utilisateurId],(err,rows,fields)=> {
    if (err){
      console.log("error " + err)
      res.sendStatus(404)
      return
    }
    const plante = rows.map((row) => {
      return {idPlante:row.idPlante,idPlanteUtilisateur:row.id_plante_utilisateur, nomFr:row.nomFr,nomPersonnel:row.nom_personnel, usageMilieu:row.usageMilieu,
        image :{idImage:row.id_image,url:row.url},type:{idType:row.id_type,nom:row.nom}}
    })
    res.json(plante)
  })   
})
//localhost:3003
app.listen(3000, () => {
  console.log("Server is up and listening on 3000 ...")
})

