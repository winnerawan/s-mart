<?php
/**
 */
namespace App\Http\Bundle\Service\Providers;
use Illuminate\Support\Facades\Route;
use Illuminate\Foundation\Support\Providers;
/**
 */
class RouteServiceProvider extends Providers\RouteServiceProvider
{
	/**
	 */
	protected $namespace = '';

	/**
	 */
	public function map(){
		Route::middleware(['api'])
			->namespace('App\Http\Bundle\Service\Api\Controllers')
			->group(app_path('Http/Bundle/Service/Api/routes/routes.php'));
	}
}
