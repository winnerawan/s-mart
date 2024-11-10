<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\User;

use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use App\Database\Smart;
use App\Database\Smart\UserStatus;
use App\Service\Api;
use App\Support;

/**
 */
class Status extends Api\User\UserValidator
{

	/**
	 */
	protected $validatorRules = [
        'user' => 'required',
		'status' => 'required'
    ];
	/**
	 */
	protected $newUser;
	protected $status;
    /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		$this->newUser = Smart\User::select()
			->where('user.id', $this->validatorData->get('user'))
			->first();
		if ($this->newUser==null) {
			return false;
		}
		$this->status = Smart\UserStatus::select()
			->where('id', $this->validatorData->get('status'))
			->first();
		if ($this->status==null) {
			return;
		}
		return true;
	}

	/**
	 */
	public function update(){
		return Smart\User::transaction(function(){
			$user = $this->getUser();
			$this->newUser->user_status_id = $this->status->id;
			$this->newUser->updated_user_id = $user->id;
			$this->newUser->save();

			if ($this->status->id==UserStatus::STATUS_ACTIVE_ID) {
				$this->sendNotification($user->name, $this->newUser->name, $this->newUser->getUsername()->username);
			}
		});
	}

	/**
	 */
	public function sendNotification($reviewer, $newUserName, $recepient){
        // var_dump($recepients);
		$headings = array (
		  'en' => 'Account Approval'
		);
	    $content = array(
		  'en' => strtoupper(sprintf('Dear %s, %s Telah Memverifikasi Anda', $newUserName, $reviewer))
	  	);
	  
	  $fields = array(
		'app_id' => Api\Config::get('onesignal.app_id'),
		'include_external_user_ids' => [$recepient],
		'data' => array("emergency" => false, "user_verification" => true),
		'contents' => $content,
		'headings' => $headings
	  );
	  
	  $fields = json_encode($fields);
	  
	  $ch = curl_init();
	  curl_setopt($ch, CURLOPT_URL, "https://onesignal.com/api/v1/notifications");
	  $header = sprintf('Authorization: Basic %s', Api\Config::get('onesignal.api_key'));
	  curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', $header));
	  curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
	  curl_setopt($ch, CURLOPT_HEADER, FALSE);
	  curl_setopt($ch, CURLOPT_POST, TRUE);
	  curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
	  curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
  
	  $response = curl_exec($ch);
	  curl_close($ch);
  
	  return $response;
	}
}