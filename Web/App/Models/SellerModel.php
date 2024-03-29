<?php
namespace App\Models;

use App\Database;
use PDO;

class SellerModel
{
    public function getAll()
    {
        $db = Database::getInstance();

        $sql = "SELECT * FROM `Seller`";
        $query = $db->prepare($sql);
        $query->execute();

        return $query->fetchAll();
    }

    public function getById($id)
    {
        $db = Database::getInstance();

        $sql = "SELECT * FROM `Seller` WHERE `ID` = :id LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->execute();

        return $query->fetch();
    }

    public function getByEmail($email)
    {
        $db = Database::getInstance();

        $sql = "SELECT * FROM `Seller` WHERE `Email` = :email LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':email', $email, PDO::PARAM_STR);
        $query->execute();

        return $query->fetch();
    }

    public function create($email, $phoneNumber, $name, $website, $description, $location, $password)
    {
        $db = Database::getInstance();

        $sql = "INSERT INTO `Seller` (`Email`, `PhoneNumber`, `Name`, `Website`, `Description`, `Location`, `Password`)"
            . " VALUES (:email, :phoneNumber, :name, :website, :description, :location, :password)";
        $query = $db->prepare($sql);
        $query->bindParam(':email', $email, PDO::PARAM_STR);
        $query->bindParam(':phoneNumber', $phoneNumber, PDO::PARAM_STR);
        $query->bindParam(':name', $name, PDO::PARAM_STR);
        $query->bindParam(':website', $website, PDO::PARAM_STR);
        $query->bindParam(':description', $description, PDO::PARAM_STR);
        $query->bindParam(':location', $location, PDO::PARAM_STR);
        $query->bindParam(':password', $password, PDO::PARAM_STR);
        $query->execute();
    }

    public function update($id, $email, $phoneNumber, $name, $website, $description, $location, $password)
    {
        $db = Database::getInstance();

        $sql = "UPDATE `Seller` SET"
            . " `Email` = :email,"
            . " `PhoneNumber` = :phoneNumber,"
            . " `Name` = :name,"
            . " `Website` = :website,"
            . " `Description` = :description,"
            . " `Location` = :location,"
            . " `Password` = :password"
            . " WHERE `ID` = :id LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->bindParam(':email', $email, PDO::PARAM_STR);
        $query->bindParam(':phoneNumber', $phoneNumber, PDO::PARAM_STR);
        $query->bindParam(':name', $name, PDO::PARAM_STR);
        $query->bindParam(':website', $website, PDO::PARAM_STR);
        $query->bindParam(':description', $description, PDO::PARAM_STR);
        $query->bindParam(':location', $location, PDO::PARAM_STR);
        $query->bindParam(':password', $password, PDO::PARAM_STR);
        $query->execute();
    }
}