<?php
namespace App\Controllers;

use App\Models;
use App\Results;
use App\Response;

class AdvertController
{
    private $advertModel;

    public function __construct()
    {
        $this->advertModel = new Models\AdvertModel();
    }
    
    public function create()
    {
        if (!empty($_POST['VehicleReg']) &&
            !empty($_POST['Description']) &&
            !empty($_POST['Price']) &&
            !empty($_POST['SellerID']))
        {
            $vehicleReg = $_POST['VehicleReg'];
            $description = $_POST['Description'];
            $price = $_POST['Price'];
            $sellerId = $_POST['SellerID'];
            
            $this->advertModel->create($vehicleReg, $description, $price, $sellerId);
            return new Results\JsonResult("Advert was created successfully.");
        }

        return new Results\JsonResult("Unable to create the Advert.", Response::BAD_REQUEST);
    }

    public function upload_image()
    {
       var_dump($_FILES['image']); // debug

       $articleId = $_FILES['image']['name'];

       // Get the file and decode it
       $data = file_get_contents($_FILES['image']['tmp_name']);
       $data = base64_decode($data);
       $image = imagecreatefromstring($data);
       file_put_contents($_FILES['image']['tmp_name'], $data);

       // Upload the file
       $target = 'images/adverts/' . $articleId . '.png';
       move_uploaded_file($_FILES['image']['tmp_name'], $target);

       // Add the image url to the database
       $imageUrl = 'http://localhost/~michael/cartrader/carshare/Web/' . $target;
       $this->advertModel->updateImage($articleId, $imageUrl);

       return new Results\JsonResult("Done");
    }

    function base64_to_jpeg($base64_string, $output_file) {
        // open the output file for writing
        $ifp = fopen( $output_file, 'wb' ); 
    
        // split the string on commas
        // $data[ 0 ] == "data:image/png;base64"
        // $data[ 1 ] == <actual base64 string>
        $data = explode( ',', $base64_string );
    
        // we could add validation here with ensuring count( $data ) > 1
        fwrite( $ifp, base64_decode( $data[ 1 ] ) );
    
        // clean up the file resource
        fclose( $ifp ); 
    
        return $output_file; 
    }

    public function read()
    {
        // Read by ID
        if (isset($_GET['id'])) 
        {
            $id = $_GET['id'];
            $advert = $this->advertModel->getById($id);
            if (!$advert)
                return new Results\JsonResult("No Advert found for the specified ID.");
            return new Results\JsonResult($advert);
        }

        // Read all
        $adverts = $this->advertModel->getAll();
        return new Results\JsonResult($adverts);
    }

    public function update()
    {
        if (!empty($_POST['ID']) &&
            !empty($_POST['VehicleReg']) &&
            !empty($_POST['Description']) &&
            !empty($_POST['Price']) &&
            !empty($_POST['SellerID']))
        {
            $id = $_POST['ID'];
            $vehicleReg = $_POST['VehicleReg'];
            $description = $_POST['Description'];
            $price = $_POST['Price'];
            $sellerId = $_POST['SellerID'];
            
            $this->advertModel->update($id, $vehicleReg, $description, $price, $sellerId);
            return new Results\JsonResult("Advert was updated successfully.");
        }

        return new Results\JsonResult("Unable to update the Advert.", Response::BAD_REQUEST);
    }
}