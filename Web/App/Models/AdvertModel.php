<?php
namespace App\Models;

use App\Database;
use PDO;

class AdvertModel
{
    public function get($id)
    {
        $db = Database::getInstance();

        $sql = "SELECT * FROM `Advert` WHERE `ID` = :id LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->execute();

        return $query->fetch();
    }

    public function getAll()
    {
        $db = Database::getInstance();

        $sql = "SELECT * FROM `Advert`";
        $query = $db->prepare($sql);
        $query->execute();

        return $query->fetchAll();
    }

}