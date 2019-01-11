<?php
header('Content-Type: application/json; charset=UTF-8');
libxml_use_internal_errors(true);
$url = 'https://hortical.com/rubrique7.html';
$DOM = new DOMDocument();
$DOM->loadHTMLFile($url);
$xpath = new DOMXPath($DOM);

$compteur = 1;

for($compteur; $compteur <= 1; $compteur++) {
  $elementsDiv = $xpath->query("//*[@id=\"contenu\"]/div[2]/div/div[2]/div[".$compteur."]");
  if (!is_null($elementsDiv)) {
    foreach ($elementsDiv as $elementDiv) {
      $nodesDiv = $elementDiv->childNodes;
      foreach ($nodesDiv as $nodeDiv) {
        if($nodeDiv->hasAttributes()) {
          $arrayLinks[$nodeDiv->nodeValue] = "https://hortical.com/".$nodeDiv->getAttribute('href');
        }
      }
    }
  }
}

$arrayPlanteFinal = array();
$arrayFamilleFinal = array();
$arrayCalendrierFinal = array();
$arrayImgFinal = array();
$arrayTypeFinal = array();

foreach ($arrayLinks as $key => $value) {


  $arrayPlant = array();


  $namePlant = trim($key);
  $urlPlant = $value;

  $DOMPlant = new DOMDocument();
  $DOMPlant->loadHTMLFile($urlPlant);
  $xpathPlant = new DOMXPath($DOMPlant);


  $elementsImage = $xpathPlant->query("//*[@id=\"contenu\"]/div[1]/img");
  foreach ($elementsImage as $elementImage) {
    if($elementImage->getAttribute('src') != "local/cache-vignettes/L250xH339/rubon1-ad5fc.jpg?1512568097") {
      $arrayImgFinal[] = "https://hortical.com/".$elementImage->getAttribute('src');
      $arrayPlant["img_url"] = "https://hortical.com/".$elementImage->getAttribute('src');
    } else {
      $arrayPlant["img_url"] = "blank";
    }
  }

  $elementsFamily = $xpathPlant->query("//*[@id=\"contenu\"]/div[1]/div");

  $firstelement = false;

  $arrayFamille = array();

  foreach ($elementsFamily as $elementFamily) {
    $nodesDiv = $elementFamily->childNodes;
    foreach ($nodesDiv as $nodeDiv) {
      if($nodeDiv->hasAttributes()) {
        if($firstelement == false) {
          $firstelement = true;
          $familelNameFrench = trim(preg_replace("/\([^)]+\)/","",$nodeDiv->nodeValue), ")");
          $familelNameFrench = trim(preg_replace("/\([^)]+\)/","",$familelNameFrench), ")");
          $familelNameFrench = trim(preg_replace("/\)/","",$familelNameFrench), ")");
          $arrayFamille["nom_francais"] = preg_replace('/^\p{Z}+|\p{Z}+$/u', '', $familelNameFrench);
        } else {
          $arrayFamille["nom_latin"] = trim($nodeDiv->nodeValue);
        }
      }
    }
  }
  $arrayPlant["famille"] = $arrayFamille;
  if(!in_array($arrayFamille["nom_francais"], $arrayFamilleFinal)) {
    $arrayTempFamille = array();
    $arrayTempFamille["nom_latin"] = $arrayFamille["nom_latin"];
    $arrayFamilleFinal[$arrayFamille["nom_francais"]] = $arrayTempFamille;
  }

  $elementsLatinName = $xpathPlant->query("//*[@id=\"contenu\"]/div[1]/span[1]/i");
  foreach ($elementsLatinName as $elementLatinName) {
    $arrayPlant["nom_latin"] = trim($elementLatinName->nodeValue);
  }


  $elementsDescription = $xpathPlant->query("//*[@id=\"contenu\"]/div[5]/div");
  foreach ($elementsDescription as $elementDescription) {
    $arrayPlant["description"] = str_replace(["[1]", "[2]", "?23", "?"], "", trim($elementDescription->nodeValue));
  }

  $arrayCalendrier = array();

  for($cpt = 2; $cpt <= 20; $cpt++) {
    $typeActionName = "";
    $typesAction = $xpathPlant->query("//*[@id=\"contenu\"]/div[2]/div[".$cpt."]/div[1]");
    foreach ($typesAction as $typeAction) {
      $typeActionName = str_replace(["\n", "\r", "   "], ["", "", " "], trim($typeAction->nodeValue));
      $typeActionName = trim(preg_replace("/\([^)]+\)/","",$typeActionName), ")");
      $typeActionName = trim(preg_replace("/\)/","",$typeActionName), ")");
      $typeActionName = preg_replace('/^\p{Z}+|\p{Z}+$/u', '', $typeActionName);

      if($typeAction->hasChildNodes()) {
        if(!in_array($typeActionName, $arrayCalendrierFinal) && $typeActionName != "") {
          $arrayCalendrierFinal[] = $typeActionName;
        }
      }
    }

    $elementsCalendriers = $xpathPlant->query("//*[@id=\"contenu\"]/div[2]/div[".$cpt."]/table");
    foreach ($elementsCalendriers as $elementCalendriers) {
      $arrayMonths = array();
      $arrayMonths = recursiveChildPrint($elementCalendriers, $arrayMonths);
      if ($typeActionName != "" && !empty($arrayMonths)) {
        $arrayCalendrier[$typeActionName] = $arrayMonths;
      }
    }
  }

  if(!empty($arrayCalendrier)) {
    $arrayPlant["calendriers"] = $arrayCalendrier;
  }

  $divIndex = 4;
  $elementsCouleurs = $xpathPlant->query("//*[@id=\"contenu\"]/div[".$divIndex."]");
  $finalElementCouleur = null;
  foreach ($elementsCouleurs as $elementCouleurs) {
    $finalElementCouleur = $elementCouleurs;
  }
  while($finalElementCouleur->getAttribute('class') !== 'detail') {
    $divIndex = $divIndex+1;
    $elementsCouleurs = $xpathPlant->query("//*[@id=\"contenu\"]/div[".$divIndex."]");
    foreach ($elementsCouleurs as $elementCouleurs) {
      $finalElementCouleur = $elementCouleurs;
    }
  }

  $arrayDetailPlante = array();

  for($tableIndex = 1; $tableIndex <= 20; $tableIndex++) {


    $elementsCouleurs = $xpathPlant->query("//*[@id=\"contenu\"]/div[".$divIndex."]/table[".$tableIndex."]");
    foreach ($elementsCouleurs as $elementCouleurs) {

      $typeDetail = -1;
      $childNodes = $elementCouleurs->childNodes;
      foreach ($childNodes as $childNode) {

        $childNodesChilds = $childNode->childNodes;
        foreach ($childNodesChilds as $childNodesChild) {
          if($childNodesChild->hasAttributes() && $childNodesChild->getAttribute('class') == 'titre_mots_articles') {
            $childNodesChild->nodeValue = utf8_encode(trim($childNodesChild->nodeValue));
            if($childNodesChild->nodeValue == "couleur des fleurs")
            {
              $typeDetail = 1;
            }
            if($childNodesChild->nodeValue == "sol")
            {
              $typeDetail = 2;
            }
            if($childNodesChild->nodeValue == "type")
            {
              $typeDetail = 3;
            }
            if($childNodesChild->nodeValue == "usage ou milieu")
            {
              $typeDetail = 4;
            }
            if($childNodesChild->nodeValue == "exposition")
            {
              $typeDetail = 5;
            }
          }
          if($childNodesChild->hasAttributes() && $childNodesChild->getAttribute('class') == 'mots_articles') {
            $arrayValues = array();
            $arrayValues = explode("/", $childNodesChild->nodeValue);
            for ($i = 0; $i < count($arrayValues); $i++) {
              $value = $arrayValues[$i];
              $value = preg_replace('/^\p{Z}+|\p{Z}+$/u', '', $value);
              $value = str_replace("\r", "", trim($value));
              $value = str_replace("\n", "", trim($value));
              $arrayValues[$i] = explode(" ", $value, 1)[0];
            }
            switch ($typeDetail) {
              case 1:
              $arrayDetailPlante["couleur des fleurs"] = $arrayValues;
              break;
              case 2:
              $arrayDetailPlante["sol"] = $arrayValues;
              break;
              case 3:
              foreach ($arrayValues as $value) {
                if(!in_array($value, $arrayTypeFinal)) {
                  $arrayTypeFinal[] = $value;
                }
              }
              $arrayDetailPlante["type"] = $arrayValues;
              break;
              case 4:
              $arrayDetailPlante["usage ou milieu"] = $arrayValues;
              break;
            }
          }
          if($childNodesChild->hasAttributes() && $childNodesChild->getAttribute('class') == 'mois_vide') {
            if($typeDetail == 5) {
              $childNodesExposition = $childNodesChild->childNodes;
              foreach ($childNodesExposition as $childNodeExposition) {
                if($childNodeExposition->hasAttributes()) {
                  $arrayDetailPlante["exposition"] = trim($childNodeExposition->getAttribute('title'));
                }
              }
            }
          }
        }
      }
    }
  }
  $arrayPlant["details"] = $arrayDetailPlante;
  $arrayPlanteFinal[$key] = $arrayPlant;
}

$requeteSautLigne = "\n";


$requeteCalendrierCreate = "CREATE TABLE 'action_calendrier' (
  'idActionCalendrier' int(11) NOT NULL,
  'type' varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requeteCalendrierAlter = "ALTER TABLE 'action_calendrier'
ADD PRIMARY KEY ('idActionCalendrier'),
MODIFY 'idActionCalendrier' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;\n";

$arrayCalendrierFinal = array_unique($arrayCalendrierFinal);
sort($arrayCalendrierFinal, SORT_STRING);
$requeteCalendrier = "INSERT INTO 'action_calendrier' ('type') VALUES\n";
foreach ($arrayCalendrierFinal as $calendrier) {
  if($calendrier != end($arrayCalendrierFinal)) {
    $requeteCalendrier .= "('".$calendrier."'),\n";
  } else {
    $requeteCalendrier .= "('".$calendrier."');";
  }
}

$requeteFamilleCreate = "CREATE TABLE 'famille' (
  'idFamille' int(11) NOT NULL,
  'nom' varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requeteFamilleAlter = "ALTER TABLE 'famille'
ADD PRIMARY KEY ('idFamille'),
MODIFY 'idFamille' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;\n";


// $arrayFamilleFinal = array_unique($arrayFamilleFinal);
ksort($arrayFamilleFinal, SORT_STRING);
$requeteFamille = "INSERT INTO 'famille' ('nom', 'nom_latin') VALUES\n";
foreach ($arrayFamilleFinal as $famille => $familleValue) {
  if($famille != key(end($arrayFamilleFinal))) {
    $requeteFamille .= "('".$famille."', '".$familleValue['nom_latin']."'),\n";
  } else {
    $requeteFamille .= "('".$famille."', '".$familleValue['nom_latin']."');";
  }
}


$requeteImgCreate = "CREATE TABLE 'image' (
  'idImage' int(11) NOT NULL,
  'url' varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requeteImgAlter = "ALTER TABLE 'image'
ADD PRIMARY KEY ('idImage'),
MODIFY 'idImage' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;\n";


$requeteImg = "INSERT INTO 'image' ('url') VALUES\n";
foreach ($arrayImgFinal as $img) {
  if($img != end($arrayImgFinal)) {
    $requeteImg .= "('".$img."'),\n";
  } else {
    $requeteImg .= "('".$img."');";
  }
}

$requeteTypeCreate = "CREATE TABLE 'type' (
  'idType' int(11) NOT NULL,
  'nom' varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requeteTypeAlter = "ALTER TABLE 'type'\n
ADD PRIMARY KEY ('idType'),\n
MODIFY 'idType' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;";

sort($arrayTypeFinal, SORT_STRING);
$requeteType = "INSERT INTO 'type' ('nom') VALUES\n";
foreach ($arrayTypeFinal as $type) {
  if($type != end($arrayTypeFinal)) {
    $requeteType .= "('".$type."'),\n";
  } else {
    $requeteType .= "('".$type."');";
  }
}



$requetePlanteCreate = "CREATE TABLE 'plante' (
  'idPlante' int(11) NOT NULL,
  'id_image' int(11) DEFAULT NULL,
  'id_famille' int(11) NOT NULL,
  'nomFr' varchar(32) NOT NULL,
  'description' text NOT NULL,
  'nomLatin' varchar(32) NOT NULL,
  'couleurFleurs' varchar(255) DEFAULT NULL,
  'exposition' varchar(25) DEFAULT NULL,
  'sol' varchar(255) DEFAULT NULL,
  'usageMilieu' varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requetePlanteAlter = "ALTER TABLE 'plante'
ADD PRIMARY KEY ('idPlante'),
ADD KEY 'fk_plante_famille' ('id_famille'),
ADD KEY 'fk_plante_image' ('id_image'),
MODIFY 'idPlante' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1,
ADD CONSTRAINT 'fk_plante_famille' FOREIGN KEY ('id_famille') REFERENCES 'famille' ('idFamille'),
ADD CONSTRAINT 'fk_plante_image' FOREIGN KEY ('id_image') REFERENCES 'image' ('idImage');\n";


$requetePlanteTypeCreate = "CREATE TABLE 'plante_type' (
  'idPlanteType' int(11) NOT NULL,
  'id_plante' int(11) NOT NULL,
  'id_type' int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requetePlanteTypeAlter = "ALTER TABLE 'plante_type'
ADD PRIMARY KEY ('idPlanteType'),
ADD KEY 'fk_plante_type_plante' ('id_plante'),
ADD KEY 'fk_plante_type_type' ('id_type'),
MODIFY 'idPlanteType' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1,
ADD CONSTRAINT 'fk_plante_type_plante' FOREIGN KEY ('id_plante') REFERENCES 'plante' ('idPlante'),
ADD CONSTRAINT 'fk_plante_type_type' FOREIGN KEY ('id_type') REFERENCES 'type' ('idType');\n";

$requetePlanteCalendrierCreate = "CREATE TABLE 'plante_calendrier' (
  'idPlanteCalendrier' int(11) NOT NULL,
  'id_plante' int(11) NOT NULL,
  'id_action_calendrier' int(11) NOT NULL,
  'id_mois' int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requetePlanteCalendrierAlter = "ALTER TABLE 'plante_calendrier'
ADD PRIMARY KEY ('idPlanteCalendrier'),
ADD KEY 'fk_plantecalendrier_plante' ('id_plante'),
ADD KEY 'fk_plantecalendrier_mois' ('id_mois'),
ADD KEY 'fk_plantecalendrier_action_calendrier' ('id_action_calendrier'),
MODIFY 'idPlanteCalendrier' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1,
ADD CONSTRAINT 'fk_plantecalendrier_action_calendrier' FOREIGN KEY ('id_action_calendrier') REFERENCES 'action_calendrier' ('idActionCalendrier'),
ADD CONSTRAINT 'fk_plantecalendrier_mois' FOREIGN KEY ('id_mois') REFERENCES 'mois' ('idMois'),
ADD CONSTRAINT 'fk_plantecalendrier_plante' FOREIGN KEY ('id_plante') REFERENCES 'plante' ('idPlante');\n";


$requetePlante = "INSERT INTO 'plante' ('id_image', 'id_famille', 'nomFr', 'description', 'nomLatin', 'couleurFleurs', 'exposition', 'sol', 'usageMilieu') VALUES\n";
$requetePlanteType = "INSERT INTO 'plante_type' ('id_plante', 'id_type') VALUES\n";
$requetePlanteCalendrier = "INSERT INTO 'plante_calendrier' ('id_plante', 'id_action_calendrier', 'id_mois') VALUES\n";
foreach ($arrayPlanteFinal as $plantName => $plant) {
  $id_image = array_search($plant["img_url"], $arrayImgFinal);
  if($id_image === false){
    $id_image = "";
  } else {
    $id_image += 1;
  }
  $id_famille = array_search($plant["famille"]["nom_francais"], array_keys($arrayFamilleFinal)) + 1;
  $nomFr = $plantName;
  $desciption = "";
  if(array_key_exists("description", $plant)) {
    $desciption = $plant["description"];
  }
  $nomLatin = $plant["nom_latin"];
  $couleurFleurs = "";
  if(array_key_exists("couleur des fleurs", $plant["details"])) {
    if(is_array($plant["details"]["couleur des fleurs"])){
      foreach ($plant["details"]["couleur des fleurs"] as $couleur) {
        if($couleur != end($plant["details"]["couleur des fleurs"])) {
          $couleurFleurs .= $couleur.", ";
        } else {
          $couleurFleurs .= $couleur;
        }
      }
    } else {
      $couleurFleurs = $plant["details"]["couleur des fleurs"];
    }
  }
  $exposition = "";
  if(array_key_exists("exposition", $plant["details"])) {
    if(is_array($plant["details"]["exposition"])){
      foreach ($plant["details"]["exposition"] as $expositionValue) {
        if($expositionValue != end($plant["details"]["exposition"])) {
          $exposition .= $expositionValue.", ";
        } else {
          $exposition .= $expositionValue;
        }
      }
    } else {
      $exposition = $plant["details"]["exposition"];
    }
  }
  $sol = "";
  if(array_key_exists("sol", $plant["details"])) {
    if(is_array($plant["details"]["sol"])){
      foreach ($plant["details"]["sol"] as $solValue) {
        if($solValue != end($plant["details"]["sol"])) {
          $sol .= $solValue.", ";
        } else {
          $sol .= $solValue;
        }
      }
    } else {
      $sol = $plant["details"]["sol"];
    }
  }
  $usageMilieu = "";
  if(array_key_exists("usage ou milieu", $plant["details"])) {
    if(is_array($plant["details"]["usage ou milieu"])){
      foreach ($plant["details"]["usage ou milieu"] as $usage) {
        if($usage != end($plant["details"]["usage ou milieu"])) {
          $usageMilieu .= $usage.", ";
        } else {
          $usageMilieu .= $usage;
        }
      }
    } else {
      $usageMilieu = $plant["details"]["usage ou milieu"];
    }
  }
  if(array_key_exists("type", $plant["details"])) {
    if(is_array($plant["details"]["type"])){
      foreach ($plant["details"]["type"] as $type) {
        if($type != end($plant["details"]["type"])) {
          $requetePlanteType .= "(".(array_search($plantName, array_keys($arrayPlanteFinal))+1).", ".(array_search($type, $arrayTypeFinal)+1)."),\n";
        }
        else {
          $requetePlanteType .= "(".(array_search($plantName, array_keys($arrayPlanteFinal))+1).", ".(array_search($type, $arrayTypeFinal)+1).")";
        }
      }
    } else {
      $requetePlanteType .= "(".(array_search($plantName, array_keys($arrayPlanteFinal))+1).", ".(array_search($plant["details"]["type"], $arrayTypeFinal)+1).")";
    }
  }

  if(array_key_exists("calendriers", $plant)) {
    foreach ($plant["calendriers"] as $typeCalendrier => $calendrier) {
      if(is_array($calendrier)) {
        foreach ($calendrier as $mois) {
          $requetePlanteCalendrier .= "(".(array_search($plantName, array_keys($arrayPlanteFinal))+1).", ".(array_search($typeCalendrier, $arrayCalendrierFinal)+1).", ".$mois.")";
          if($mois != end($calendrier)) {
            $requetePlanteCalendrier = $requetePlanteCalendrier.",\n";
          }
        }
      } else {
        if($calendrier != end($plant["calendriers"])) {
          $requetePlanteCalendrier .= "(".(array_search($plantName, array_keys($arrayPlanteFinal))+1).", ".(array_search($typeCalendrier, $arrayCalendrierFinal)+1).", ".$calendrier.")),\n";
        } else {
          $requetePlanteCalendrier .= "(".(array_search($plantName, array_keys($arrayPlanteFinal))+1).", ".(array_search($typeCalendrier, $arrayCalendrierFinal)+1).", ".$calendrier."))";
        }
      }
      if($typeCalendrier != end(array_keys($plant["calendriers"]))) {
        $requetePlanteCalendrier = $requetePlanteCalendrier.",\n";
      }
    }
  }

  // $requetePlanteCalendrier

  if($plant != end($arrayPlanteFinal)) {
    var_dump($plant);
    $requetePlante .= "(".$id_image.", ".$id_famille.", '".$nomFr."', '".$desciption."', '".$nomLatin."', '".$couleurFleurs."', '".$exposition."', '".$sol."', '".$usageMilieu."'),\n";
    if(array_key_exists("type", $plant["details"])){
      $requetePlanteType = $requetePlanteType.",\n";
    }
    if(array_key_exists("calendriers", $plant)){
      $requetePlanteCalendrier = $requetePlanteCalendrier.",\n";
    }
  } else {
    $requetePlante .= "(".$id_image.", ".$id_famille.", '".$nomFr."', '".$desciption."', '".$nomLatin."', '".$couleurFleurs."', '".$exposition."', '".$sol."', '".$usageMilieu."');";
    $requetePlanteType = $requetePlanteType.";";

    if(array_key_exists("calendriers", $plant)){
      $requetePlanteCalendrier = $requetePlanteCalendrier.";";
    } else {
      $requetePlanteCalendrier = substr($requetePlanteCalendrier, 0, -2).";";
    }
  }
}

$fpCalendrierRequete = fopen('calendrier.sql', 'w');
fwrite($fpCalendrierRequete, $requeteCalendrierCreate);
fwrite($fpCalendrierRequete, $requeteSautLigne);
fwrite($fpCalendrierRequete, $requeteCalendrierAlter);
fwrite($fpCalendrierRequete, $requeteSautLigne);
fwrite($fpCalendrierRequete, $requeteCalendrier);
fclose($fpCalendrierRequete);

$fpFamilleRequete = fopen('famille.sql', 'w');
fwrite($fpFamilleRequete, $requeteFamilleCreate);
fwrite($fpFamilleRequete, $requeteSautLigne);
fwrite($fpFamilleRequete, $requeteFamilleAlter);
fwrite($fpFamilleRequete, $requeteSautLigne);
fwrite($fpFamilleRequete, $requeteFamille);
fclose($fpFamilleRequete);

$fpImgRequete = fopen('image.sql', 'w');
fwrite($fpImgRequete, $requeteImgCreate);
fwrite($fpImgRequete, $requeteSautLigne);
fwrite($fpImgRequete, $requeteImgAlter);
fwrite($fpImgRequete, $requeteSautLigne);
fwrite($fpImgRequete, $requeteImg);
fclose($fpImgRequete);

$fpTypeRequete = fopen('type.sql', 'w');
fwrite($fpTypeRequete, $requeteTypeCreate);
fwrite($fpTypeRequete, $requeteSautLigne);
fwrite($fpTypeRequete, $requeteTypeAlter);
fwrite($fpTypeRequete, $requeteSautLigne);
fwrite($fpTypeRequete, $requeteType);
fclose($fpTypeRequete);

$fpPlanteRequete = fopen('plante.sql', 'w');
fwrite($fpPlanteRequete, $requetePlanteCreate);
fwrite($fpPlanteRequete, $requeteSautLigne);
fwrite($fpPlanteRequete, $requetePlanteAlter);
fwrite($fpPlanteRequete, $requeteSautLigne);
fwrite($fpPlanteRequete, $requetePlante);
fclose($fpPlanteRequete);

$fpPlanteTypeRequete = fopen('plante_type.sql', 'w');
fwrite($fpPlanteTypeRequete, $requetePlanteTypeCreate);
fwrite($fpPlanteTypeRequete, $requeteSautLigne);
fwrite($fpPlanteTypeRequete, $requetePlanteTypeAlter);
fwrite($fpPlanteTypeRequete, $requeteSautLigne);
fwrite($fpPlanteTypeRequete, $requetePlanteType);
fclose($fpPlanteTypeRequete);

$fpPlanteCalendrierRequete = fopen('plante_calendrier.sql', 'w');
fwrite($fpPlanteCalendrierRequete, $requetePlanteCalendrierCreate);
fwrite($fpPlanteCalendrierRequete, $requeteSautLigne);
fwrite($fpPlanteCalendrierRequete, $requetePlanteCalendrierAlter);
fwrite($fpPlanteCalendrierRequete, $requeteSautLigne);
fwrite($fpPlanteCalendrierRequete, $requetePlanteCalendrier);
fclose($fpPlanteCalendrierRequete);



function recursiveChildPrint($object, $arrayMonths) {
  if($object->hasChildNodes()) {

    if($object->tagName == 'strong') {
      array_push($arrayMonths,$object->nodeValue);
    }
    $childNodes = $object->childNodes;
    foreach ($childNodes as $childNode) {
      $arrayMonths = recursiveChildPrint($childNode, $arrayMonths);
    }
  }
  return $arrayMonths;
}

?>
