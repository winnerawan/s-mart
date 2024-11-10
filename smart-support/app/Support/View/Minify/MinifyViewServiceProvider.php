<?php
/**
 */
namespace App\Support\View\Minify;

use Illuminate\View\ViewServiceProvider;
use Illuminate\View\Engines\CompilerEngine;

/**
 */
class MinifyViewServiceProvider extends ViewServiceProvider
{
	/**
	 */
	public function registerBladeEngine($resolver){
		$this->app->singleton('blade.compiler', function(){
			return new MinifyBladeCompiler($this->app['files'], $this->app['config']['view.compiled']);
		});
		$resolver->register('blade', function(){
			return new CompilerEngine($this->app['blade.compiler']);
		});
	}
}