<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Supplier;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Create extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'name' => 'required|string',
    ];

     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        return true;
    }

    /**
     */
    public function create() {
        return Smart\User::transaction(function(){
            $now = \Carbon\Carbon::now();
            $customer = new Smart\Supplier();
            $customer->name = $this->validatorData->get('name');
            $customer->address = $this->validatorData->get('address');
            $customer->phone = $this->validatorData->get('phone');

            // $category->description = $this->validatorData->get('description');
            $customer->save();
		});
    }

    

}