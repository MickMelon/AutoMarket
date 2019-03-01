<?php
namespace App;

use App\Controllers;
use App\Results;

class Router
{
    /**
     * Gets the specified controller and action and acts on it to 
     * effectively start the app.
     */
    public function start()
    {
        // Set the controller and action depending on the parameters
        // that are set.
        // e.g. index.php?controller=articles&action=index
        // would set $controller to articles and $action to index
        $controller = isset($_GET['c']) ? strtolower($_GET['c']) : 'article';
        $action = isset($_GET['a']) ? strtolower($_GET['a']) : 'index';

        // Check to see if the class and method exist, if they do, call it.
        $className = 'App\Controllers\\' . ucfirst($controller) . 'Controller';
        if (class_exists($className, true))  
        {
            $controllerClass = new $className();
            if (method_exists($controllerClass, $action))
            {
                $result = $controllerClass->{ $action }();
                $result->execute();
                return;
            }    
        }

        // Call the index of the article controller if the specified 
        // controller or action were not found.
        Results\ViewResult::error()->execute();           
    }
}