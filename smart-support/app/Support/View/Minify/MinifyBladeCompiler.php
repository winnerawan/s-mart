<?php
/**
 */
namespace App\Support\View\Minify;

use Illuminate\View\Compilers\BladeCompiler;

/**
 */
class MinifyBladeCompiler extends BladeCompiler
{
	/**
	 */
	public function compileString($value){
		return Minify::minify(parent::compileString($value));
	}
}