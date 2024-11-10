<?php
/*-------------------------------------------------------------------------
 * /sale
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'SaleController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'SaleController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'SaleController@update'
]);

$router->post('/updatePosition', [
	'as' => '/updatePosition',
	'uses' => 'SaleController@updatePosition'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'SaleController@delete'
]);