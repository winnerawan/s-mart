<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\User;

use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Password extends Api\User\UserValidator
{

	/**
	 */
	protected $validatorRules = [
		'identity' => 'required|numeric',
		'password' => 'nullable|string',
		'new_password' => 'required|string|min:4|max:32|confirmed'
    ];
	/**
	 */
	protected $user;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		if ((int) $this->validatorData->get('identity') == 1) {
			$status = $this->getUser()->getPassword()->isPassword($this->validatorData->get('password'));
			if (!$status) {
				return false;
			}
		}
		return true;
	}

	/**
	 */
	public function update(){
		return Smart\User::transaction(function(){
			$user = $this->getUser();
			$password = $user->getPassword();
			$password->setPassword($this->validatorData->get('new_password'));
			$password->updated_user_id = $user->id;
			$password->is_updated = 1;
			$password->save();
		});
	}

	/**
	 */
	public function response(){
		$user =  $this->getUser();
        $response['user'] = $user;
		$response['user']['username'] = $user->getUsername()->username;
		$response['user']['is_verified'] = $user->getUserStatus()->isStatusActive();
		$response['user']['is_admin'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->isRoleAdmin() : false;
		$response['user']['role'] = $user->getUserRole() != null ? $user->getUserRole()->getRole()->name : null;
		$response['user']['image'] = $user->getUserPhoto()  != null ? sprintf('https:%s%s', Api\Config::get('media.file'), $user->getUserPhoto()->getMedia()->file) : null;
		$response['user']['home'] = $user->getUserHome() != null ? sprintf('%s %s', $user->getUserHome()->getHome()->getBlock()->name, $user->getUserHome()->getHome()->name) : null;
		return $response;
	}
}