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

$arrayFinal = array();

foreach ($arrayLinks as $key => $value) {

  // $key = "abricotier";
  // $valueAbricot = 'https://hortical.com/article1532.html';
  // $valueAbsinthe = 'https://hortical.com/article926.html';
  // $valueAzolleFausseFougère = "https://hortical.com/article2444.html";

  $arrayPlant = array();
  // echo mb_detect_encoding(trim($key))."\n";
  $namePlant = trim($key);
  $urlPlant = $value;

  // echo $namePlant."\n";



  $DOMPlant = new DOMDocument();
  $DOMPlant->loadHTMLFile($urlPlant);
  $xpathPlant = new DOMXPath($DOMPlant);


  $elementsFamily = $xpathPlant->query("//*[@id=\"contenu\"]/div[1]/div");

  $firstelement = false;


  $arrayFamille = array();

  foreach ($elementsFamily as $elementFamily) {
    $nodesDiv = $elementFamily->childNodes;
    foreach ($nodesDiv as $nodeDiv) {
      if($nodeDiv->hasAttributes()) {
        if($firstelement == false) {
          $firstelement = true;
          // echo mb_detect_encoding(trim($nodeDiv->nodeValue))."\n";
          $arrayFamille["nom_francais"] = trim($nodeDiv->nodeValue);
          // echo "famille français : ".$nodeDiv->nodeValue."\n";
        } else {
          $arrayFamille["nom_latin"] = trim($nodeDiv->nodeValue);
          $arrayFamille["lien"] = trim($nodeDiv->getAttribute('href'));
          // echo "famille latin : ".$nodeDiv->nodeValue."\n";
          // echo "link famille : ".$nodeDiv->getAttribute('href')."\n";
        }
      }
    }
  }
  $arrayPlant["famille"] = $arrayFamille;

  $elementsLatinName = $xpathPlant->query("//*[@id=\"contenu\"]/div[1]/span[1]/i");
  foreach ($elementsLatinName as $elementLatinName) {
    $arrayPlant["nom_latin"] = trim($elementLatinName->nodeValue);
    // echo "latin name : ".$elementLatinName->nodeValue."\n";
  }


  $elementsDescription = $xpathPlant->query("//*[@id=\"contenu\"]/div[5]/div");
  foreach ($elementsDescription as $elementDescription) {
    $arrayPlant["description"] = trim($elementDescription->nodeValue);
    // echo "description : ".$elementDescription->nodeValue."\n";
  }

  $arrayCalendrier = array();

  for($cpt = 2; $cpt <= 20; $cpt++) {
    $typeActionName = "";
    $typesAction = $xpathPlant->query("//*[@id=\"contenu\"]/div[2]/div[".$cpt."]/div[1]");
    foreach ($typesAction as $typeAction) {
      $typeActionName = trim($typeAction->nodeValue);
      if($typeAction->hasChildNodes()) {
        // echo "calendrier ".($cpt-1)." : ".trim($typeAction->nodeValue)." ";
      }
    }

    $elementsCalendriers = $xpathPlant->query("//*[@id=\"contenu\"]/div[2]/div[".$cpt."]/table");
    foreach ($elementsCalendriers as $elementCalendriers) {
      $arrayMonths = array();
      $arrayMonths = recursiveChildPrint($elementCalendriers, $arrayMonths);

      $arrayCalendrier[$typeActionName] = $arrayMonths;
      foreach ($arrayMonths as $month) {
        // echo $month." ";
      }
      // echo "\n";
    }
  }

  $arrayPlant["calendriers"] = $arrayCalendrier;

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
            switch ($typeDetail) {
              case 1:
              $arrayDetailPlante["couleur des fleurs"] = trim($childNodesChild->nodeValue) ;
              // echo "couleur des fleurs : ".$childNodesChild->nodeValue."\n";
              break;
              case 2:
              $arrayDetailPlante["sol"] = trim($childNodesChild->nodeValue);
              // echo "sol : ".$childNodesChild->nodeValue."\n";
              break;
              case 3:
              $arrayDetailPlante["type"] = trim($childNodesChild->nodeValue);
              // echo "type : ".$childNodesChild->nodeValue."\n";
              break;
              case 4:
              $arrayDetailPlante["usage ou milieu"] = trim($childNodesChild->nodeValue);
              // echo "usage ou milieu : ".$childNodesChild->nodeValue."\n";
              break;
            }
          }
          if($childNodesChild->hasAttributes() && $childNodesChild->getAttribute('class') == 'mois_vide') {
            if($typeDetail == 5) {
              $childNodesExposition = $childNodesChild->childNodes;
              // echo "exposition : ";
              foreach ($childNodesExposition as $childNodeExposition) {
                if($childNodeExposition->hasAttributes()) {
                  $arrayDetailPlante["exposition"] = trim($childNodeExposition->getAttribute('title'));
                  // echo $childNodeExposition->getAttribute('title')." ";
                }
              }
              // echo "\n";
            }
          }
        }
      }
    }
  }
  $arrayPlant["details"] = $arrayDetailPlante;
  $arrayFinal[$key] = $arrayPlant;
  // echo "\n";
}

echo json_encode($arrayFinal, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);


// echo "<pre>"; var_dump($arrayFinal); echo "</pre>";






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
