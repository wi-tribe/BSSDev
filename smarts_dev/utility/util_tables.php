<?php
define( CNIC_LOG_TABLE,    				'CNIC_VERIFICATIONS_LOG');        # core_product_info
define( SMARTS_LOG_TABLE,    			'SMARTS_LOG');        # core_product_info
//-------------------------Product Packaging tables ------------------------------//
define( SERVICE_TABLE,    			'service');                 # core_product_info
define( PRODUCT_GROUP_TABLE,        'product_groups');          # product_groups
define( PRODUCT_SLA_TABLE,          'product_sla');             # product_sla
define( PRODUCT_CYCLE_TABLE,        'product_cycles');          # product_cycles
define( PRODUCT_VOLUMES_TABLE,      'product_volumes');         # product_volumes
define( PRODUCTS_TABLE,     		'service_products');        # service_products
define( SERVICE_PRODUCT_TABLE,    	'core_product_info');       # core_product_info

define( PACKAGE_TYPE_TABLE,         'package_types');           # package_types
define( PACKAGES_TABLE,     		'service_packages');        # service_packages
define( PACKAGE_PRODUCTS_TABLE,     'package_products');        # package_products
define( PACKAGE_COMMITMENT_TABLE,   'commitments');             # commitments

//----------------------CUSTOMER INFO tables ------------------------------//

define( PROSPECTS_TABLE,            'prospects');               # prospects
define( CUSTOMER_TYPE_TABLE,        'customer_types');          # customer_types
define( CUSTOMER_TABLE,			    'customers');               # customers
define( CUSTOMER_ADDRESS_TABLE,     'customer_addresses');      # customer_addresses
define( CUSTOMER_PRODUCT_TABLE,		'customer_products');		# customer_products
define( CUSTOMER_ACCOUNTS_TABLE,	'service_accounts');        # service_accounts
define( CUSTOMER_COMMITMENT_TABLE,	'customer_commitments');    # customer_commitments

//-------------------------SALES tables ------------------------------//

define( SALES_TABLE,       			'customer_sales');          # customers_sales
define( SALE_CHANNELS_TABLE,     	'sale_channels');           # sale_channels
define( SALE_USERS_TABLE,     		'sale_users');              # sale_users
define( SHOPS_TABLE,     			'shops');                   # shops
define( SALE_LEVELS_TABLE,     		'sale_levels');             # sale_levels
define( REGIONS_TABLE,     			'regions');                 # regions

//-------------------------BILLING tables ------------------------------//

define( BILLING_TABLE,				'customer_billing_info');	# customer_billing_info
define( ADJUSTMENT_TABLE,    		'adjustments');             # adjustments

define( TRIAL_ADJUSTMENT_TABLE,    	'trial_adjustment');       # trial_adjustment
define( TRIAL_INVOICE_TABLE,		'trial_customer_invoice');	# trial_customer_invoices
define( TRIAL_INVOICE_DETAIL_TABLE,	'trial_invoice_detail');	# trial_invoice_details

define( INVOICE_TABLE,				'customer_invoices');		# customer_invoices
define( INVOICE_DETAILS_TABLE,		'invoice_details');         # invoice_details

define( PAYMENT_MODES_TABLE,		'payment_modes');           # payment_modes
define( RECEIPT_TABLE,				'customer_receipt');		# customer_receipt

//-------------------------INVENTORY tables ------------------------------//
define( INVENTORY_TABLE,     		'inventory');               # inventory


?>