<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Customer;

use Carbon\Carbon;
use Illuminate\Http\Request;
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
        'customer' => 'required|numeric',
        'name' => 'nullable|string',
    ];

    protected $customer;

     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        $this->customer = Smart\Customer::select()
            ->where('customer.id', $this->validatorData->get('customer'))
            ->first();
        if (!$this->customer) {
            return false;
        }
        return true;
    }

    /**
     */
    public function update() {
        return Smart\User::transaction(function(){
            $this->customer->name = $this->validatorData->get('name');
            // $this->customer->description = $this->validatorData->get('description');
            // $this->customer->updated_user_id = $this->getUser()->id;
            // $this->customer->is_updated = 1;
            $this->customer->save();
		});
    }

    /**
     */
    public function updatePosition() {
        return Smart\User::transaction(function(){
            $this->customer->position = $this->validatorData->get('position');
            // $this->customer->updated_user_id = $this->getUser()->id;
            // $this->customer->is_updated = 1;
            $this->customer->save();
		});
    }
}