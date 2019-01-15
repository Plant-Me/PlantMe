<?php
header('Content-Type: application/json; charset=UTF-8');
libxml_use_internal_errors(true);
$url = 'https://hortical.com/rubrique7.html';
$DOM = new DOMDocument();
$DOM->loadHTMLFile($url);
$xpath = new DOMXPath($DOM);


for($compteurGlobal = 11; $compteurGlobal <= 26; $compteurGlobal++) {
  $arrayLinks = array();
  //*[@id="contenu"]/div[2]/div/div[2]/div[1]
  $elementsDiv = $xpath->query("//*[@id=\"contenu\"]/div[2]/div/div[2]/div[".$compteurGlobal."]");
  if ($elementsDiv->length > 0) {
    foreach ($elementsDiv as $elementDiv) {
      $nodesDiv = $elementDiv->childNodes;
      foreach ($nodesDiv as $nodeDiv) {
        if($nodeDiv->hasAttributes()) {
          $arrayLinks[$nodeDiv->nodeValue] = "https://hortical.com/".$nodeDiv->getAttribute('href');
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
      $firstLetter = substr($namePlant, 0, 1);
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
              if(trim($nodeDiv->nodeValue) != "_hors AGP III") {
                $arrayFamille["nom_latin"] = trim($nodeDiv->nodeValue);
              }
            }
          }
        }
      }
      $arrayPlant["famille"] = $arrayFamille;
      if(!empty($arrayFamille)) {
        if(in_array("nom_francais", array_keys($arrayFamille))){
          if(!in_array($arrayFamille["nom_francais"], $arrayFamilleFinal)) {
            $arrayTempFamille = array();
            if(in_array("nom_latin", array_keys($arrayFamille))){
              $arrayTempFamille["nom_latin"] = $arrayFamille["nom_latin"];
            }
            $arrayFamilleFinal[$arrayFamille["nom_francais"]] = $arrayTempFamille;
          }
        }
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
                  $value = trim(explode(',', $value)[0]);
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
      echo "Plante : ".$key."\n";
      flush();
      ob_flush();
      $arrayPlant["details"] = $arrayDetailPlante;
      $arrayPlanteFinal[$key] = $arrayPlant;
    }

    $fpCalendrierJson = fopen('action_calendrier_'.$firstLetter.'.json', 'w');
    fwrite($fpCalendrierJson, json_encode($arrayCalendrierFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE));
    fclose($fpCalendrierJson);

    $fpFamilleJson = fopen('famille_'.$firstLetter.'.json', 'w');
    fwrite($fpFamilleJson, json_encode($arrayFamilleFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE));
    fclose($fpFamilleJson);

    $fpImgJson = fopen('image_'.$firstLetter.'.json', 'w');
    fwrite($fpImgJson, json_encode($arrayImgFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE));
    fclose($fpImgJson);

    $fpTypeJson = fopen('type_'.$firstLetter.'.json', 'w');
    fwrite($fpTypeJson, json_encode($arrayTypeFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE));
    fclose($fpTypeJson);

    $fpPlanteJson = fopen('plante_'.$firstLetter.'.json', 'w');
    fwrite($fpPlanteJson, json_encode($arrayPlanteFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE));
    fclose($fpPlanteJson);
  }
}

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

$requeteSautLigne = "\n";




?>
