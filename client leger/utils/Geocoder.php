<?php
// services/Geocoder.php

class Geocoder {
    
    private $baseUrl = "https://nominatim.openstreetmap.org/search";
    
    /**
     * Géocode une adresse complète
     * @param string $address Adresse complète (ex: "12 rue de la paix, Drancy, France")
     * @return array|null ['latitude' => float, 'longitude' => float] ou null si erreur
     */
    public function geocode($address) {
        // Nominatim demande un User-Agent
        $options = [
            'http' => [
                'header' => "User-Agent: NeigeEtSoleil/1.0\r\n"
            ]
        ];
        $context = stream_context_create($options);
        
        // Construction de l'URL
        $url = $this->baseUrl . '?' . http_build_query([
            'q' => $address,
            'format' => 'json',
            'limit' => 1
        ]);
        
        // Appel API
        $response = @file_get_contents($url, false, $context);
        
        if ($response === false) {
            return null;
        }
        
        $data = json_decode($response, true);
        
        // Vérifier si on a un résultat
        if (empty($data) || !isset($data[0]['lat']) || !isset($data[0]['lon'])) {
            return null;
        }
        
        return [
            'latitude' => (float) $data[0]['lat'],
            'longitude' => (float) $data[0]['lon']
        ];
    }
    
    /**
     * Géocode à partir de la ville et du code postal
     * @param string $ville
     * @param string $codePostal
     * @return array|null
     */
    public function geocodeByCity($ville, $codePostal = '') {
        $address = $ville;
        if (!empty($codePostal)) {
            $address = $codePostal . ' ' . $ville;
        }
        $address .= ', France'; // On ajoute le pays
        
        return $this->geocode($address);
    }
}