<?php
/*-------------------------------------------------------------------------
 * /purchase
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'PurchaseController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'PurchaseController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'PurchaseController@update'
]);

$router->post('/updateTempQty', [
	'as' => '/updateTempQty',
	'uses' => 'PurchaseController@updateTempQty'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'PurchaseController@delete'
]);