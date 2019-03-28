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
            
            $id = $this->advertModel->create($vehicleReg, $description, $price, $sellerId);
            return new Results\JsonResult(array(
                'Message' => 'Advert created successfully.',
                'ArticleID' => $id
            ));
        }

        return new Results\JsonResult(array('Message' => 'Unable to create advert.'), Response::BAD_REQUEST);
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

       return new Results\JsonResult(array('Message' => 'Image uploaded successfully.'));
    }

    public function read()
    {
        // Read by ID
        if (isset($_GET['id'])) 
        {
            $id = $_GET['id'];
            $advert = $this->advertModel->getById($id);
            if (!$advert)
                return new Results\JsonResult(array('Message' => 'No advert found for specified ID.'), Response::BAD_REQUEST);
            return new Results\JsonResult(array(
                'Message' => 'Advert read successfully.',
                'Advert' => $advert));
        }

        // Read all
        $adverts = $this->advertModel->getAll();
        return new Results\JsonResult(array(
            'Message' => 'Adverts read successfully.',
            'Adverts' => $adverts));
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
            return new Results\JsonResult(array('Message' => 'Advert updated successfully.'));
        }

        return new Results\JsonResult(array('Message' => 'Unable to update the advert.'), Response::BAD_REQUEST);
    }
}