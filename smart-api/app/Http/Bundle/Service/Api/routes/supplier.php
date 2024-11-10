<?php
/*-------------------------------------------------------------------------
 * /supplier
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'SupplierController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'SupplierController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'SupplierController@update'
]);

$router->post('/updatePosition', [
	'as' => '/updatePosition',
	'uses' => 'SupplierController@updatePosition'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'SupplierController@delete'
]);