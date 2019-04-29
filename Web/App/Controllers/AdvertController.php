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
        $file = "test.txt";
        file_put_contents($file, "Test");
        
        $current = file_get_contents($file);


       $articleId = $_FILES['image']['name'];

       $current .= "ArticleID: $articleId \n";
       file_put_contents($file, $current);

       // Get the file and decode it
       $data = file_get_contents($_FILES['image']['tmp_name']);
       $data = base64_decode($data);
       $image = imagecreatefromstring($data);
       file_put_contents($_FILES['image']['tmp_name'], $data);

       $current .= "Got here\n";

       // Upload the file
       $target = 'images/adverts/' . $articleId . '.png';

       $current .= $_FILES['image']['tmp_name'] . "**";
       move_uploaded_file($_FILES['image']['tmp_name'], $target);

       $current .= "Moved file\n";

       // Add the image url to the database
       $imageUrl = 'https://mayar.abertay.ac.uk/~1800833/mobile/' . $target;
       $this->advertModel->updateImage($articleId, $imageUrl);

        $current .= "Last\n";
        file_put_contents($file, $current);

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
        //echo sizeof($adverts);
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