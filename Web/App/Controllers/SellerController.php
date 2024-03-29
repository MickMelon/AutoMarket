<?php
namespace App\Controllers;

use App\Models;
use App\Results;

class SellerController 
{
    private $sellerModel;

    public function __construct() 
    {
        $this->sellerModel = new Models\SellerModel();
    }

    public function create()
    {       
        if (!empty($_POST['Email']) &&
            !empty($_POST['PhoneNumber']) &&
            !empty($_POST['Name']) &&
            !empty($_POST['Website']) &&
            !empty($_POST['Description']) &&
            !empty($_POST['Location']) &&
            !empty($_POST['Password']))
        {
            $email = $_POST['Email'];
            $phoneNumber = $_POST['PhoneNumber'];
            $name = $_POST['Name'];
            $website = $_POST['Website'];
            $description = $_POST['Description'];
            $location = $_POST['Location'];
            $password = $_POST['Password'];
            
            $this->sellerModel->create($email, $phoneNumber, $name, $website, $description, $location, $password);
            return new Results\JsonResult(array('Message' => 'Seller was created successfully.'));
        }

        return new Results\JsonResult(array('Message' => 'Unable to create seller.'), Response::BAD_REQUEST);
    }

    public function read()
    {
        // Read by ID
        if (isset($_GET['id'])) 
        {
            $id = $_GET['id'];
            $seller = $this->sellerModel->getById($id);
            if (!$seller)
                return new Results\JsonResult(array('Message' => 'No seller found for specified ID.'));
            return new Results\JsonResult(array(
                'Message' => 'Seller read successfully.',
                'Seller' => $seller));
        }

        // Read by Email
        if (isset($_GET['email']))
        {
            $email = $_GET['email'];
            $seller = $this->sellerModel->getByEmail($email);
            if (!$seller)
                return new Results\JsonResult(array('Message' => 'No seller found for specified email.'));
                return new Results\JsonResult(array(
                    'Message' => 'Seller read successfully.',
                    'Seller' => $seller));
        }

        // Read all
        $sellers = $this->sellerModel->getAll();
        return new Results\JsonResult(array(
            'Message' => 'Sellers read successfully.',
            'Sellers' => $sellers));
    }

    public function update()
    {
        if (!empty($_POST['ID']) &&
            !empty($_POST['Email']) &&
            !empty($_POST['PhoneNumber']) &&
            !empty($_POST['Name']) &&
            !empty($_POST['Website']) &&
            !empty($_POST['Description']) &&
            !empty($_POST['Location']) &&
            !empty($_POST['Password']))
        {
            $id = $_POST['ID'];
            $email = $_POST['Email'];
            $phoneNumber = $_POST['PhoneNumber'];
            $name = $_POST['Name'];
            $website = $_POST['Website'];
            $description = $_POST['Description'];
            $location = $_POST['Location'];
            $password = $_POST['Password'];

            $this->sellerModel->update($id, $email, $phoneNumber, $name, $website, $description, $location, $password);
            return new Results\JsonResult(array('Message' => 'Seller updated successfully.'));
        }

        return new Results\JsonResult(array('Message' => 'Unable to update seller.'), Response::BAD_REQUEST);
    }

    public function delete()
    {

    }
}