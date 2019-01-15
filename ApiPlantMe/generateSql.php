<?php


header('Content-Type: application/json; charset=UTF-8');

$firstLetter = 'a';


$arrayPlanteFinal = array();
$arrayFamilleFinal = array();
$arrayCalendrierFinal = array();
$arrayImgFinal = array();
$arrayTypeFinal = array();

$arrayImgFinal[] = "https://hortical.com/local/cache-vignettes/L250xH339/rubon1-ad5fc.jpg?1512568097";

for($compteur = 1; $compteur <= 26; $compteur++) {
  if($compteur != 1) {
    $firstLetter = ++$firstLetter;
  }
  $arrayActionCalendrier = array();
  $arrayFamille = array();
  $arrayImg = array();
  $arrayType = array();
  $arrayPlante = array();
  if(file_exists('action_calendrier_'.$firstLetter.'.json')) {
    $fpCalendrierJson = fopen('action_calendrier_'.$firstLetter.'.json', 'r');
    $stringActionCalendrier = fread($fpCalendrierJson, filesize('action_calendrier_'.$firstLetter.'.json'));
    $arrayActionCalendrier = json_decode($stringActionCalendrier, true);
    fclose($fpCalendrierJson);

    $fpFamilleJson = fopen('famille_'.$firstLetter.'.json', 'r');
    $stringFamille = fread($fpFamilleJson, filesize('famille_'.$firstLetter.'.json'));
    $arrayFamille = json_decode($stringFamille, true);
    fclose($fpFamilleJson);

    $fpImgJson = fopen('image_'.$firstLetter.'.json', 'r');
    $stringImg = fread($fpImgJson, filesize('image_'.$firstLetter.'.json'));
    $arrayImg = json_decode($stringImg, true);
    fclose($fpImgJson);

    $fpTypeJson = fopen('type_'.$firstLetter.'.json', 'r');
    $stringType = fread($fpTypeJson, filesize('type_'.$firstLetter.'.json'));
    $arrayType = json_decode($stringType, true);
    fclose($fpTypeJson);

    $fpPlanteJson = fopen('plante_'.$firstLetter.'.json', 'r');
    $stringPlante = fread($fpPlanteJson, filesize('plante_'.$firstLetter.'.json'));
    $arrayPlante = json_decode($stringPlante, true);
    fclose($fpPlanteJson);

    foreach ($arrayActionCalendrier as $key) {
      $key = trim(explode(',', $key)[0]);
      if(!in_array($key, $arrayCalendrierFinal)) {
        $arrayCalendrierFinal[] = $key;
      }
    }
    foreach($arrayFamille as $key => $value) {
      if(!in_array($key, $arrayFamilleFinal)) {
        $arrayFamilleFinal[$key] = $value;
      }
    }
    foreach($arrayImg as $key) {
      if(!in_array($key, $arrayImgFinal)) {
        $arrayImgFinal[] = $key;
      }
    }
    foreach($arrayType as $key) {
      if(!in_array($key, $arrayTypeFinal)) {
        $arrayTypeFinal[] = $key;
      }
    }
    foreach($arrayPlante as $key => $value) {
      if(!in_array($key, $arrayPlanteFinal)) {
        $arrayPlanteFinal[$key] = $value;
      }
    }
  }
}

// $fpFamilleJson = fopen('famille_complete.json', 'w');
// fwrite($fpFamilleJson, json_encode($arrayFamilleFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE));
// fclose($fpFamilleJson);


$fpFamilleJson = fopen('famille_complete.json', 'r');
$stringFamilleFinal = fread($fpFamilleJson, filesize('famille_complete.json'));
$arrayFamilleFinal = json_decode($stringFamilleFinal, true);
fclose($fpFamilleJson);

$requeteSautLigne = "\n";

ksort($arrayFamilleFinal, SORT_STRING);


$requeteCalendrierCreate = "CREATE TABLE `action_calendrier` (
  `idActionCalendrier` int(11) NOT NULL,
  `type` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requeteCalendrierAlter = "ALTER TABLE `action_calendrier`
ADD PRIMARY KEY (`idActionCalendrier`),
MODIFY `idActionCalendrier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;\n";

$requeteCalendrierEntete = "INSERT INTO `action_calendrier` (`type`) VALUES\n";

$requeteFamilleCreate = "CREATE TABLE `famille` (
  `idFamille` int(11) NOT NULL,
  `nom` varchar(32) NOT NULL,
  `nomLatin` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requeteFamilleAlter = "ALTER TABLE `famille`
ADD PRIMARY KEY (`idFamille`),
MODIFY `idFamille` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;\n";


// $arrayFamilleFinal = array_unique($arrayFamilleFinal);
$requeteFamilleEntete = "INSERT INTO `famille` (`nom`, `nomLatin`) VALUES\n";


$requeteImgCreate = "CREATE TABLE `image` (
  `idImage` int(11) NOT NULL,
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requeteImgAlter = "ALTER TABLE `image`
ADD PRIMARY KEY (`idImage`),
MODIFY `idImage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;\n";


$requeteImgEntete = "INSERT INTO `image` (`url`) VALUES\n";

$requeteTypeCreate = "CREATE TABLE `type` (
  `idType` int(11) NOT NULL,
  `nom` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requeteTypeAlter = "ALTER TABLE `type`
ADD PRIMARY KEY (`idType`),
MODIFY `idType` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;\n";

$requeteTypeEntete = "INSERT INTO `type` (`nom`) VALUES\n";


$requetePlanteCreate = "CREATE TABLE `plante` (
  `idPlante` int(11) NOT NULL,
  `id_image` int(11) DEFAULT NULL,
  `id_famille` int(11) NOT NULL,
  `nomFr` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `nomLatin` varchar(255) NOT NULL,
  `couleurFleurs` varchar(255) DEFAULT NULL,
  `exposition` varchar(25) DEFAULT NULL,
  `sol` varchar(255) DEFAULT NULL,
  `usageMilieu` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requetePlanteAlter = "ALTER TABLE `plante`
ADD PRIMARY KEY (`idPlante`),
ADD KEY `fk_plante_famille` (`id_famille`),
ADD KEY `fk_plante_image` (`id_image`),
MODIFY `idPlante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1,
ADD CONSTRAINT `fk_plante_famille` FOREIGN KEY (`id_famille`) REFERENCES `famille` (`idFamille`),
ADD CONSTRAINT `fk_plante_image` FOREIGN KEY (`id_image`) REFERENCES `image` (`idImage`);\n";


$requetePlanteTypeCreate = "CREATE TABLE `plante_type` (
  `idPlanteType` int(11) NOT NULL,
  `id_plante` int(11) NOT NULL,
  `id_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requetePlanteTypeAlter = "ALTER TABLE `plante_type`
ADD PRIMARY KEY (`idPlanteType`),
ADD KEY `fk_plante_type_plante` (`id_plante`),
ADD KEY `fk_plante_type_type` (`id_type`),
MODIFY `idPlanteType` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1,
ADD CONSTRAINT `fk_plante_type_plante` FOREIGN KEY (`id_plante`) REFERENCES `plante` (`idPlante`),
ADD CONSTRAINT `fk_plante_type_type` FOREIGN KEY (`id_type`) REFERENCES `type` (`idType`);\n";

$requetePlanteCalendrierCreate = "CREATE TABLE `plante_calendrier` (
  `idPlanteCalendrier` int(11) NOT NULL,
  `id_plante` int(11) NOT NULL,
  `id_action_calendrier` int(11) NOT NULL,
  `id_mois` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

$requetePlanteCalendrierAlter = "ALTER TABLE `plante_calendrier`
ADD PRIMARY KEY (`idPlanteCalendrier`),
ADD KEY `fk_plantecalendrier_plante` (`id_plante`),
ADD KEY `fk_plantecalendrier_mois` (`id_mois`),
ADD KEY `fk_plantecalendrier_action_calendrier` (`id_action_calendrier`),
MODIFY `idPlanteCalendrier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1,
ADD CONSTRAINT `fk_plantecalendrier_action_calendrier` FOREIGN KEY (`id_action_calendrier`) REFERENCES `action_calendrier` (`idActionCalendrier`),
ADD CONSTRAINT `fk_plantecalendrier_mois` FOREIGN KEY (`id_mois`) REFERENCES `mois` (`idMois`),
ADD CONSTRAINT `fk_plantecalendrier_plante` FOREIGN KEY (`id_plante`) REFERENCES `plante` (`idPlante`);\n";


$requetePlanteEntete = "INSERT INTO `plante` (`id_image`, `id_famille`, `nomFr`, `description`, `nomLatin`, `couleurFleurs`, `exposition`, `sol`, `usageMilieu`) VALUES\n";
$requetePlanteTypeEntete = "INSERT INTO `plante_type` (`id_plante`, `id_type`) VALUES\n";
$requetePlanteCalendrierEntete = "INSERT INTO `plante_calendrier` (`id_plante`, `id_action_calendrier`, `id_mois`) VALUES\n";


$arrayCalendrierFinal = array_unique($arrayCalendrierFinal);
sort($arrayCalendrierFinal, SORT_STRING);
$requeteCalendrier = "";
foreach ($arrayCalendrierFinal as $calendrier) {
  if($calendrier != end($arrayCalendrierFinal)) {
    $requeteCalendrier .= "('".$calendrier."'),\n";
  } else {
    $requeteCalendrier .= "('".$calendrier."');";
  }
}

ksort($arrayFamilleFinal, SORT_STRING);
$requeteFamille = "";
foreach ($arrayFamilleFinal as $famille => $familleValue) {
  $familleValueNomLatin = "";
  if(!empty($familleValue)) {
    if($familleValue["nom_latin"] != "_hors AGP III") {
      $familleValueNomLatin = $familleValue["nom_latin"];
    }
  }
  $arrayKeys = array_keys($arrayFamilleFinal);
  if($famille != end($arrayKeys)) {
    $requeteFamille .= "('".$famille."', '".$familleValueNomLatin."'),\n";
  } else {
    $requeteFamille .= "('".$famille."', '".$familleValueNomLatin."');";
  }
}

$requeteImg = "";
foreach ($arrayImgFinal as $img) {
  if($img != end($arrayImgFinal)) {
    $requeteImg .= "('".$img."'),\n";
  } else {
    $requeteImg .= "('".$img."');";
  }
}


$arrayTypeFinal = array_unique($arrayTypeFinal);
sort($arrayTypeFinal, SORT_STRING);
$requeteType = "";
foreach ($arrayTypeFinal as $type) {
  if($type != end($arrayTypeFinal)) {
    $requeteType .= "('".$type."'),\n";
  } else {
    $requeteType .= "('".$type."');";
  }
}


$requetePlante = "";
$requetePlanteType = "";
$requetePlanteCalendrier = "";
foreach ($arrayPlanteFinal as $plantName => $plant) {
  $id_image = array_search($plant["img_url"], $arrayImgFinal);
  if($id_image === false){
    $id_image = 1;
  } else {
    $id_image += 1;
  }
  $id_famille = "";
  if (in_array("nom_francais", array_keys($plant["famille"]))) {
    $id_famille = array_search($plant["famille"]["nom_francais"], array_keys($arrayFamilleFinal)) + 1;
  }
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
      foreach ($calendrier as $mois) {
        if($mois != "gel") {
          $requetePlanteCalendrier .= "(".(array_search($plantName, array_keys($arrayPlanteFinal))+1).", ".(array_search($typeCalendrier, $arrayCalendrierFinal)+1).", ".$mois.")";
          if($mois != end($calendrier)) {
            $requetePlanteCalendrier = $requetePlanteCalendrier.",\n";
          }
        } else {
          $requetePlanteCalendrier = substr($requetePlanteCalendrier, 0, -2);
        }

      }
      $arrayKeys = array_keys($plant["calendriers"]);
      if($typeCalendrier != end($arrayKeys)) {
        $requetePlanteCalendrier = $requetePlanteCalendrier.",\n";
      }
    }
  }

  if($plant != end($arrayPlanteFinal)) {
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

$fpCalendrierRequete = fopen('action_calendrier.sql', 'w');
fwrite($fpCalendrierRequete, $requeteCalendrierCreate);
fwrite($fpCalendrierRequete, $requeteSautLigne);
fwrite($fpCalendrierRequete, $requeteCalendrierAlter);
fwrite($fpCalendrierRequete, $requeteSautLigne);
fwrite($fpCalendrierRequete, $requeteCalendrierEntete);
fwrite($fpCalendrierRequete, $requeteCalendrier);
fclose($fpCalendrierRequete);

$fpFamilleRequete = fopen('famille.sql', 'w');
fwrite($fpFamilleRequete, $requeteFamilleCreate);
fwrite($fpFamilleRequete, $requeteSautLigne);
fwrite($fpFamilleRequete, $requeteFamilleAlter);
fwrite($fpFamilleRequete, $requeteSautLigne);
fwrite($fpFamilleRequete, $requeteFamilleEntete);
fwrite($fpFamilleRequete, $requeteFamille);
fclose($fpFamilleRequete);

$fpImgRequete = fopen('image.sql', 'w');
fwrite($fpImgRequete, $requeteImgCreate);
fwrite($fpImgRequete, $requeteSautLigne);
fwrite($fpImgRequete, $requeteImgAlter);
fwrite($fpImgRequete, $requeteSautLigne);
fwrite($fpImgRequete, $requeteImgEntete);
fwrite($fpImgRequete, $requeteImg);
fclose($fpImgRequete);

$fpTypeRequete = fopen('type.sql', 'w');
fwrite($fpTypeRequete, $requeteTypeCreate);
fwrite($fpTypeRequete, $requeteSautLigne);
fwrite($fpTypeRequete, $requeteTypeAlter);
fwrite($fpTypeRequete, $requeteSautLigne);
fwrite($fpTypeRequete, $requeteTypeEntete);
fwrite($fpTypeRequete, $requeteType);
fclose($fpTypeRequete);

$fpPlanteRequete = fopen('plante.sql', 'w');
fwrite($fpPlanteRequete, $requetePlanteCreate);
fwrite($fpPlanteRequete, $requeteSautLigne);
fwrite($fpPlanteRequete, $requetePlanteAlter);
fwrite($fpPlanteRequete, $requeteSautLigne);
fwrite($fpPlanteRequete, $requetePlanteEntete);
fwrite($fpPlanteRequete, $requetePlante);
fclose($fpPlanteRequete);

$fpPlanteTypeRequete = fopen('plante_type.sql', 'w');
fwrite($fpPlanteTypeRequete, $requetePlanteTypeCreate);
fwrite($fpPlanteTypeRequete, $requeteSautLigne);
fwrite($fpPlanteTypeRequete, $requetePlanteTypeAlter);
fwrite($fpPlanteTypeRequete, $requeteSautLigne);
fwrite($fpPlanteTypeRequete, $requetePlanteTypeEntete);
fwrite($fpPlanteTypeRequete, $requetePlanteType);
fclose($fpPlanteTypeRequete);

$fpPlanteCalendrierRequete = fopen('plante_calendrier.sql', 'w');
fwrite($fpPlanteCalendrierRequete, $requetePlanteCalendrierCreate);
fwrite($fpPlanteCalendrierRequete, $requeteSautLigne);
fwrite($fpPlanteCalendrierRequete, $requetePlanteCalendrierAlter);
fwrite($fpPlanteCalendrierRequete, $requeteSautLigne);
fwrite($fpPlanteCalendrierRequete, $requetePlanteCalendrierEntete);
fwrite($fpPlanteCalendrierRequete, $requetePlanteCalendrier);
fclose($fpPlanteCalendrierRequete);


echo json_encode($arrayCalendrierFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
echo json_encode($arrayFamilleFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
echo json_encode($arrayImgFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
echo json_encode($arrayTypeFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
echo json_encode($arrayPlanteFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);


?>
