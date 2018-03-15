<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the
 * installation. You don't have to use the web site, you can
 * copy this file to "wp-config.php" and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * MySQL settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://codex.wordpress.org/Editing_wp-config.php
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'data');

/** MySQL database username */
define('DB_USER', 'root');

/** MySQL database password */
define('DB_PASSWORD', '');

/** MySQL hostname */
define('DB_HOST', 'localhost');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8mb4');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'U-Cp|{ambe5q~ZK&>u/%dLgH~^yebn[E7Mc8:3sQ=;+E8|g-b:zN9arm!!R&_+2n');
define('SECURE_AUTH_KEY',  'ZZm-:nOyFvXfYjbhUc9U:nb&+)6!HEet .B1#d-1>sI6@8A9xW-MP u|/,k_/jX5');
define('LOGGED_IN_KEY',    'YNPy4LxvjwcA$s5$>,gr0} B<+DG/@+[|o/&4$mXNFCP:u_5(/k:ka?)RQq,sRK9');
define('NONCE_KEY',        'a=}J4HNYAA@)Nq-J8>#FVc85CYwN*0&&.P~/OE$],4a3h2UE]rJK1-80p>WwEgD`');
define('AUTH_SALT',        'uMFH%ledFi]J,T,XBo d_-<W^$p|)9&mLN+}T?7%F-mLv|LM$rf+ Kgxs?|7%4,Q');
define('SECURE_AUTH_SALT', 'Te&g[ub2HJ/Dn|l3NAV?,iP?R kC=&c>2Bi-PM)E6:vrkL 7dO6[O5#f]jmQ,,#y');
define('LOGGED_IN_SALT',   's{5G1i#.-U:;3![+KX{U%qI#.Mkehv,,l@8R5WLnIII^lxG$XDU-T[h> /l[~yMY');
define('NONCE_SALT',       'D~iGgOJN!DL&GCNG(wxkIWIj[$MM}j*r^3)N?I?-)DllG|JD!c*[]Cyuw*Fo)V,N');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the Codex.
 *
 * @link https://codex.wordpress.org/Debugging_in_WordPress
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
