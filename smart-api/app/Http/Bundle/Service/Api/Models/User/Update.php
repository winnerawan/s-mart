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
class Update extends Api\User\UserValidator
{

	/**
	 */
	protected $validatorRules = [
		'name' => 'nullable',
		'phone' => 'nullable'
    ];
	/**
	 */
	protected $user;
	protected $firstName;
	protected $lastName;

    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->user = $this->getUser();
		return true;
	}

	/**
	 */
	protected function validateName()
	{
        $name = trim($this->validatorData->get('name'));
        $last_name = (strpos($name, ' ') === false) ? '' : preg_replace('#.*\s([\w-]*)$#', '$1', $name);
        $first_name = trim( preg_replace('#'.preg_quote($last_name,'#').'#', '', $name ) );
        $this->firstName = $first_name;
        $this->lastName = $last_name;
        $name = $name = implode(' ', [$this->firstName, $this->lastName]);
        return $name;
    }

	/**
	 */
	public function update(){
		return Smart\User::transaction(function(){
			$this->user->phone = $this->validatorData->get('phone');
			$this->user->name = $this->validateName();
			$this->user->first_name = $this->firstName;
			$this->user->last_name = $this->lastName;
			$this->user->updated_user_id = $this->user->id;
			$this->user->is_updated = 1;
			$this->user->save();
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
		$response['user']['home'] = $user->getUserHome() != null ? sprintf('%s %s', $user->getUserHome()->getHome()->getBlock()->name, $user->getUserHome()->getHome()->name) : null;
		$response['user']['home_id'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->id : null;
		$response['user']['block_id'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->block_id : null;
		$response['user']['home_name'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->name : null;
		$response['user']['block_name'] = $user->getUserHome() != null ? $user->getUserHome()->getHome()->getBlock()->name : null;
		return $response;
	}
}