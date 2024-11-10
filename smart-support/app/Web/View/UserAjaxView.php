<?php
/**
 */
namespace App\Web\View;

use App\Support\View\AjaxView;
use App\Web\User\UserModel;

/**
 */
class UserAjaxView extends AjaxView
{
	/**
	 */
	public function render(){
		return parent::render()->with('__user', new UserModel());
	}
}