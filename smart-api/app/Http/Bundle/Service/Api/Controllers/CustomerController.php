<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class CustomerController extends AbstractController
{
    /*-------------------------------------------------------------------------
     * /customer/data
     *-------------------------------------------------------------------------
     */
    public function data(Request $request)
    {
        $data = new Models\Customer\Data($request);
        if (!$data->validate()) {
            return $this->jsonError()
                ->params($data->getValidator()->errors())
                ->build();
        }
        return $this->jsonData($data->response())
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /customer/create
     *-------------------------------------------------------------------------
     */
    public function create(Request $request)
    {
        $create = new Models\Customer\Create($request);
        if (!$create->validate()) {
            return $this->jsonError()
                ->params($create->getValidator()->errors())
                ->build();
        }
        $create->create();
        return $this->jsonSuccess()
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /customer/update
     *-------------------------------------------------------------------------
     */
    public function update(Request $request)
    {
        $update = new Models\Customer\Update($request);
        if (!$update->validate()) {
            return $this->jsonError()
                ->params($update->getValidator()->errors())
                ->build();
        }
        $update->update();
        return $this->jsonSuccess()
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /customer/updatePosition
     *-------------------------------------------------------------------------
     */
    public function updatePosition(Request $request)
    {
        $updatePosition = new Models\Customer\Update($request);
        if (!$updatePosition->validate()) {
            return $this->jsonError()
                ->params($updatePosition->getValidator()->errors())
                ->build();
        }
        $updatePosition->updatePosition();
        return $this->jsonSuccess()
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /customer/delete
     *-------------------------------------------------------------------------
     */
    public function delete(Request $request)
    {
        $delete = new Models\Customer\Delete($request);
        if (!$delete->validate()) {
            return $this->jsonError()
                ->params($delete->getValidator()->errors())
                ->build();
        }
        $delete->delete();
        return $this->jsonSuccess()
            ->build();
    }
}