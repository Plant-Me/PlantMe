//load our app server usng express somehow ..
const express = require('express')
const app = express()
const morgan = require('morgan')
const mysqlAsync = require('mysql')
const mysqlSync= require('sync-mysql')
const bodyParser= require('body-parser')


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


app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())
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
  const queryString3 = "SELECT type.idType,type.nom from type,plante_type where plante_type.id_type = type.idType and plante_type.id_plante = ?"
  const queryString = "SELECT idPlante,nomFr,plante.nomLatin,description,couleurFleurs,exposition,sol,usageMilieu,famille.idFamille,famille.nom as nomFamille,image.idImage,image.url from plante inner JOIN famille on plante.id_famille = famille.idFamille INNER JOIN image on plante.id_image = image.idImage  where plante.idPlante = ?"
  const queryString2 = "SELECT * FROM plante_calendrier inner join action_calendrier on action_calendrier.idActionCalendrier = plante_calendrier.id_action_calendrier inner join mois on mois.idMois = plante_calendrier.id_mois WHERE plante_calendrier.id_plante = ?"
  
  let resultquery1 = connectionSync.query(queryString,[planteId])
    if (resultquery1.length > 0){
      let plante = resultquery1.map((row) => {
        return {idPlante:row.idPlante, nomFr:row.nomFr, nomLatin:row.nomLatin,description:row.description,
          couleurFleurs:row.couleurFleurs, exposition:row.exposition,
          sol:row.sol, usageMilieu:row.usageMilieu,type : [],image : {idImage:row.idImage,url:row.url},
          famille : {idFamille:row.idFamille,nom:row.nomFamille}, 
          actions : []
      
        }
      })
      let resultquery3 = connectionSync.query(queryString3,[planteId])
        if (resultquery3.length > 0){
          for(let i=0;i<resultquery3.length;i++){

            plante[0].type[i] = {
              idType : resultquery3[i].idType,                
              nom : resultquery3[i].nom
            }     
          }
        }else {
          res.sendStatus(404)
          return
        }
        let resultquery2 = connectionSync.query(queryString2,[planteId])
        if (resultquery2.length > 0){
          for(let i=0;i<resultquery2.length;i++){

            plante[0].actions[i] = {
              idActionCalendrier : resultquery2[i].idActionCalendrier,
              type : resultquery2[i].type,
              idMois : resultquery2[i].idMois,
              mois : resultquery2[i].nom      
            }      
          }
        }else {
          res.sendStatus(404)
          return
        }
        res.json(plante)
      }else {
      res.sendStatus(404)
      return
  }
    
  })

app.get("/utilisateur/:idUtilisateur/plante",(req,res) => {

  const utilisateurId = req.params.idUtilisateur
  const queryString1 = "select DISTINCT image.url,plante.usageMilieu,plantes_utilisateur.id_plante_utilisateur,plantes_utilisateur.nom_personnel,plante.nomFr from image,plante,plantes_utilisateur,plante_type where image.idImage = plante.id_image and plante_type.id_plante = plante.idPlante and plantes_utilisateur.id_plante = plante.idPlante and plantes_utilisateur.id_utilisateur = ?"
  const queryString3 = "SELECT  id_plante from plantes_utilisateur where id_utilisateur = ?"
  const queryString2 = "SELECT type.idType,type.nom from type,plante_type where plante_type.id_type = type.idType and plante_type.id_plante = ?"
  let resultquery1 = connectionSync.query(queryString1,[utilisateurId])
  if (resultquery1.length > 0){
    let plante = resultquery1.map((row) => {
    return {url:row.url,usageMilieu:row.usageMilieu,idPlanteUtilisateur:row.id_plante_utilisateur,nomPersonnel:row.nom_personnel,nomFr:row.nomFr,type:[]}
    })

    let resultquery3 = connectionSync.query(queryString3,[utilisateurId])
    if (resultquery3.length > 0){
      console.log("g,eg,e " +resultquery3.length )
      for(let i=0;i<resultquery3.length;i++){
        let resultquery2 = connectionSync.query(queryString2,[resultquery3[i].id_plante])
        if (resultquery2.length > 0){
          for(let j=0;j<resultquery2.length;j++){
            
            plante[i].type[j] = {
              idType : resultquery2[j].idType,                
              nom : resultquery2[j].nom
            }     
          }
          
        }else {
          console.log("not found1")
          res.sendStatus(404)
          return
        }
      }
      
    }else {
      console.log("not found")
      res.sendStatus(404)
      return
    }

    



    res.json(plante)
  }else {
    console.log("not found")
    res.sendStatus(404)
    return
  }
    
})

app.get("/utilisateur/:idUtilisateur/plante/:idPlanteUtilisateur",(req,res) => {

  const idPlanteUtilisateur = req.params.idPlanteUtilisateur
  const idUtilisateur = req.params.idUtilisateur
  const queryString = "select * from plantes_utilisateur where id_plante_utilisateur = ? and id_utilisateur = ?"
  const queryString5 = "SELECT type.idType,type.nom from type,plante_type where plante_type.id_type = type.idType and plante_type.id_plante = (SELECT id_plante from plantes_utilisateur where id_plante_utilisateur = ? and id_utilisateur = ?)"
  const queryString3 = "select plante.nomFr,famille.nom as nomFamille,type.nom as nomType from plante,famille,type,plante_type where plante.id_famille = famille.idFamille and type.idType = plante_type.id_type and plante_type.id_plante = plante.idPlante and plante.idPlante = (SELECT id_plante from plantes_utilisateur where id_plante_utilisateur = ? and id_utilisateur = ?)"
  const queryString2 = "SELECT plante_action_utilisateur.date,action_utilisateur.idActionUtilisateur,action_utilisateur.nomAction,plantes_utilisateur.id_plante_utilisateur FROM plante_action_utilisateur inner join action_utilisateur on plante_action_utilisateur.id_action_utilisateur = action_utilisateur.idActionutilisateur inner join plantes_utilisateur on plante_action_utilisateur.id_plante_utilisateur = plantes_utilisateur.id_plante_utilisateur where plantes_utilisateur.id_plante_utilisateur = ?"
  const queryString4 ="SELECT * FROM plante_calendrier inner join action_calendrier on action_calendrier.idActionCalendrier = plante_calendrier.id_action_calendrier inner join mois on mois.idMois = plante_calendrier.id_mois WHERE plante_calendrier.id_plante = (SELECT id_plante from plantes_utilisateur where id_plante_utilisateur = ? and id_utilisateur = ?)"
  let resultquery1 = connectionSync.query(queryString,[idPlanteUtilisateur,idUtilisateur])
    if (resultquery1.length > 0){
      let plante = resultquery1.map((row) => {
        return {nomPersonnel:row.nom_personnel,actionsUtilisateur:[],plante : {} 
        }
      })
        let resultquery2 = connectionSync.query(queryString2,[idPlanteUtilisateur])
        if (resultquery2.length > 0){
          for(let i=0;i<resultquery2.length;i++){
            
            plante[0].actionsUtilisateur[i] = {
              idActionUtilisateur : resultquery2[i].idActionUtilisateur,
              nomAction : resultquery2[i].nomAction,
              date : resultquery2[i].date      
            }      
          }
          
        }else {
          console.log("not found")
          res.sendStatus(404)
          return
        }
        let resultquery3 = connectionSync.query(queryString3,[idPlanteUtilisateur,idUtilisateur])
        if (resultquery3.length > 0){    
            
            plante[0].plante = {
              nomPlante : resultquery3[0].nomFr,
              nomFamille : resultquery3[0].nomFamille,
              type : [],
              actionPlante : []
            }
            let resultquery5 = connectionSync.query(queryString5,[idPlanteUtilisateur,idUtilisateur])
            if (resultquery5.length > 0){    
                
              for(let i=0;i<resultquery5.length;i++){
                
                plante[0].plante.type[i] = {
                  idType : resultquery5[i].idType,                
                  nom : resultquery5[i].nom,
                }      
              } 
              
            }else {
             
              res.sendStatus(404)
              return
            }
            let resultquery4 = connectionSync.query(queryString4,[idPlanteUtilisateur,idUtilisateur])
            if (resultquery4.length > 0){    
                
              for(let i=0;i<resultquery4.length;i++){
                
                plante[0].plante.actionPlante[i] = {
                  idActionCalendrier : resultquery4[i].idActionCalendrier,
                  idType : resultquery4[i].type,
                  idMois : resultquery4[i].idMois,
                  mois : resultquery4[i].nom      
                }      
              } 
              
            }else {
             
              res.sendStatus(404)
              return
            }
          
        }else {
         
          res.sendStatus(404)
          return
        }
        


        res.json(plante)
      }else {
        
      res.sendStatus(404)
      return
  }
    
})

app.post("/plantesUtilisateur",(req,res) => {

  let plantesUtilisateur = req.body
  const queryString = "insert into plantes_utilisateur (id_utilisateur,id_plante,nom_personnel) values (?,?,?)"
  console.log("plante " + plantesUtilisateur)
  for(let i=0;i<plantesUtilisateur.length;i++){
            
    connectionAsync.query(queryString,[plantesUtilisateur[i].id_utilisateur,plantesUtilisateur[i].id_plante,plantesUtilisateur[i].nom_personnel],(err,rows,fields)=> {
      if (err){
        console.log("error " + err)
        res.sendStatus(404)
        return
      }
      else {
        res.status(200).send('PlantesUtilisateur inserted')
      }   
  })
  }
  
 
})


app.delete("/plantesUtilisateur",(req,res) => {

  let plantesUtilisateur = req.body
  const queryString = "DELETE FROM plantes_utilisateur WHERE plantes_utilisateur.id_plante_utilisateur = ?"
  for(let i=0;i<plantesUtilisateur.length;i++){
  
    connectionAsync.query(queryString,[plantesUtilisateur[i].id_plante_utilisateur],(err,rows,fields)=> {
      if (err){
        console.log("error " + err)
        res.sendStatus(404)
        return
      }
      else {
        res.status(200).send('PlantesUtilisateur deleted')
      }   
  })
  
  }
  
})



//localhost:3000
app.listen(3000, () => {
  console.log("Server is up and listening on 3000 ...")
})

