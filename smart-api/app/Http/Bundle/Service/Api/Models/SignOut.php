<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models;

use App\Database\Smart;
use App\Service\Api;

/**
 */
class SignOut extends Api\User\UserValidator
{
	/**
	 */
	public function signout(){
		Smart\User::transaction(function(){
			$token = $this->getToken();
			$token->token_status_id = Smart\TokenStatus::STATUS_REVOKED_ID;
			$token->updated_user_id = $this->getUser()->id;
			$token->is_updated = 1;
			$token->save();
		});
	}
}

