<?php 
namespace App;
use App\Config;
use App\Helpers\AuthHelper;
/**
 * Contains all the functions for setting up and rendering views.
 */
class View
{
    /**
     * The path to the header file.
     */
    const HEADER_FILE = 'App/Views/Templates/header.php';
    /**
     * The path to the footer file.
     */
    const FOOTER_FILE = 'App/Views/Templates/footer.php';
    /**
     * The variables used on the view page.
     */
    private $data = array();
    /**
     * The file location of the view page to be displayed (located in app/Views)
     */
    private $file = false;
    /**
     * Create a new view from the specified template name.
     * 
     * @param string $template The template name (e.g. Articles/index)
     * 
     * @return View
     */
    public function __construct($template)
    {
        $file = 'App/Views/' . $template . '.php';
        
        if (file_exists($file)) 
            $this->file = $file;
        else 
            $this->file = 'App/Views/Pages/error.php'; 
    }
    /**
     * Add the variables that need to be displayed on the view.
     * 
     * Example use:
     * $value = 'Number 21'
     * $view->assign('name', $value);
     * 
     * So in the view, you use the variable like $name to access the value set
     * in $value.
     * 
     * @param string $variable The desired name of the variable.
     * @param object $value The value of the variable to be used.
     * 
     * @return void
     */
    public function assign($variable, $value)
    {
        $this->data[$variable] = $value;
    }
    /**
     * Display the view with the header and footer.
     * 
     * @return void
     */
    public function render()
    {
        $this->assignDefaultVariables();
        extract($this->data); 
        include(View::HEADER_FILE);
        include($this->file);
        include(View::FOOTER_FILE);
    }
    /**
     * Assigns the variables that will be used commonly amongst
     * the different views.
     * 
     * @return void
     */
    private function assignDefaultVariables()
    {
        $this->assign('siteTitle', Config::SITE_TITLE);
        $this->assign('loggedIn', AuthHelper::isLoggedIn());
        $this->assign('isAdmin', AuthHelper::isAdmin());
    }
}