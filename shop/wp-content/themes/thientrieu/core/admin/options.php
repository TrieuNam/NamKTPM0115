<?php
    if ( ! class_exists( 'HuyKira_Theme_Options' ) ) {
        class HuyKira_Theme_Options {
            public $args = array();
            public $sections = array();
            public $theme;
            public $ReduxFramework;
            public function __construct() {
                if ( ! class_exists( 'ReduxFramework' ) ) {
                    return;
                }
                // This is needed. Bah WordPress bugs.  ;)
                if ( true == Redux_Helpers::isTheme( __FILE__ ) ) {
                    $this->initSettings();
                } else {
                    add_action( 'plugins_loaded', array( $this, 'initSettings' ), 10 );
                }
            }
            public function initSettings() {
                $this->setArguments();
                $this->setHelpTabs();
                $this->setSections();
                if ( ! isset( $this->args['opt_name'] ) ) { // No errors please
                    return;
                }
                $this->ReduxFramework = new ReduxFramework( $this->sections, $this->args );
            }
            public function setSections() {
                $this->sections[] = array(
                    'title'  => __( 'Tùy chỉnh chung', 'HuyKira' ),
                    'desc'   => __( 'Tùy chỉnh chung của website', 'HuyKira' ),
                    'icon' => 'el-icon-font',
                    'fields' => array(
						array(
                            'id'       => 'logo',
                            'type'     => 'media',
                            'title'    => __( 'Tùy chọn logo', 'HuyKira' ),
                            'compiler' => 'true',
                            'desc'     => __( 'Cho phép dùng đuôi: jpg, png, gif', 'HuyKira' ),
                        ),
                        array(
                            'id'       => 'texttop',
                            'type'     => 'text',
                            'title'    => __( 'Tùy chọn main text', 'HuyKira' ),
                            'compiler' => 'true',
                            'desc'     => __( 'Câu chào mừng!', 'HuyKira' ),
                        ),
                        array(
                            'id'       => 'r1',
                            'type'     => 'media',
                            'title'    => __( 'Banner trái 1', 'HuyKira' ),
                            'compiler' => 'true',
                            'desc'     => __( 'Cho phép dùng đuôi: jpg, png, gif', 'HuyKira' ),
                        ),
                        array(
                            'id'       => 'link1',
                            'type'     => 'text',
                            'title'    => __( 'Link banner trái 1', 'HuyKira' ),
                            'compiler' => 'true',
                        ),
                        array(
                            'id'       => 'r2',
                            'type'     => 'media',
                            'title'    => __( 'Banner trái 2', 'HuyKira' ),
                            'compiler' => 'true',
                            'desc'     => __( 'Cho phép dùng đuôi: jpg, png, gif', 'HuyKira' ),
                        ),
                        array(
                            'id'       => 'link2',
                            'type'     => 'text',
                            'title'    => __( 'Link banner trái 2', 'HuyKira' ),
                            'compiler' => 'true',
                        ),
                         array(
                            'id'       => 'tt',
                            'type'     => 'media',
                            'title'    => __( 'Banner trung tâm', 'HuyKira' ),
                            'compiler' => 'true',
                            'desc'     => __( 'Cho phép dùng đuôi: jpg, png, gif', 'HuyKira' ),
                        ),
                        array(
                            'id'       => 'linktt',
                            'type'     => 'text',
                            'title'    => __( 'Link Banner trung tâm', 'HuyKira' ),
                            'compiler' => 'true',
                        ),
                        array(
                               'id' => 'section2-start',
                               'type' => 'section',
                               'title' => __('Thông tin liên hệ', 'HuyKira'),
                               'indent' => true 
                           ),
                                array(
                                    'id'       => 'addft',
                                    'type'     => 'text',
                                    'title'    => __( 'Nhập Địa chỉ', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),
                                array(
                                    'id'       => 'emailft',
                                    'type'     => 'text',
                                    'title'    => __( 'Nhập địa chỉ mail', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),
                                array(
                                    'id'       => 'phoneft',
                                    'type'     => 'text',
                                    'title'    => __( 'Nhập số điện thoại', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),
                                array(
                                    'id'       => 'hotft',
                                    'type'     => 'text',
                                    'title'    => __( 'Nhập số di động', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),
                                array(
                                    'id'       => 'web',
                                    'type'     => 'text',
                                    'title'    => __( 'Nhập địa chỉ web', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),
                        array(
                            'id'     => 'section2-end',
                            'type'   => 'section',
                            'indent' => false,
                        ),
                         array(
                               'id' => 'section3-start',
                               'type' => 'section',
                               'title' => __('Liên kết với mạng xã hội', 'HuyKira'),
                               'subtitle'     => __( 'Nhập full link mạng xã hội vào từng khu vực!', 'HuyKira' ),
                               'indent' => true 
                           ),
                                array(
                                    'id'       => 'fb',
                                    'type'     => 'text',
                                    'title'    => __( 'Page facebook', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),
                                array(
                                    'id'       => 'tw',
                                    'type'     => 'text',
                                    'title'    => __( 'Twitter', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),
                                array(
                                    'id'       => 'go',
                                    'type'     => 'text',
                                    'title'    => __( 'Youtube', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),
                        array(
                            'id'     => 'section3-end',
                            'type'   => 'section',
                            'indent' => false,
                        ),

                        array(
                                    'id'       => 'copyr',
                                    'type'     => 'text',
                                    'title'    => __( 'Text bản quyền', 'HuyKira' ),
                                    'compiler' => 'true',
                                ),

                        array(
                               'id' => 'section-start',
                               'type' => 'section',
                               'title' => __('Tùy chọn các chức năng khác', 'HuyKira'),
                               'indent' => true 
                           ),
                            array(
                                'id'       => 'check',
                                'type'     => 'checkbox',
                                'title'    => __('Ẩn các Menu không cần thiết', 'HuyKira'), 
                                'subtitle' => __('Tích chọn các menu muốn ẩn', 'HuyKira'),
                                'options'  => array(
                                    '1' => 'Menu Media',
                                    '2' => 'Menu Comments',
                                    '3' => 'Menu Contact',
                                    '4' => 'Menu Appearance (Theme)',
                                    '5' => 'Menu Plugins',
                                    '6' => 'Menu User',
                                    '7' => 'Menu Tools',
                                    '8' => 'Menu Settings',
                                    '9' => 'Menu Seo',
                                    '10' => 'Menu Types'
                                )
                            ),
                            array(

                                'id'       => 'hidemenu',
                                'type'     => 'switch', 
                                'title'    => __('Ẩn thanh điều kiển', 'HuyKira'),
                                'subtitle' => __('Ản thanh điều kiển admin ngoài front end khi đã đăng nhập.', 'HuyKira'),
                                'default'  => true,
                                'on'       =>'Ẩn',
                                'off'      =>'Hiện'
                            ),
                        array(
                            'id'     => 'section-end',
                            'type'   => 'section',
                            'indent' => false,
                        ),
                    )
                );
            }
            public function setHelpTabs() {
                // Custom page help tabs, displayed using the help API. Tabs are shown in order of definition.
                $this->args['help_tabs'][] = array(
                    'id'      => 'redux-help-tab-1',
                    'title'   => __( 'Theme Information 1', 'redux-framework-demo' ),
                    'content' => __( '<p>This is the tab content, HTML is allowed.</p>', 'redux-framework-demo' )
                );
                $this->args['help_tabs'][] = array(
                    'id'      => 'redux-help-tab-2',
                    'title'   => __( 'Theme Information 2', 'redux-framework-demo' ),
                    'content' => __( '<p>This is the tab content, HTML is allowed.</p>', 'redux-framework-demo' )
                );
                // Set the help sidebar
                $this->args['help_sidebar'] = __( '<p>This is the sidebar content, HTML is allowed.</p>', 'redux-framework-demo' );
            }
            /**

             * All the possible arguments for Redux.

             * For full documentation on arguments, please refer to: https://github.com/ReduxFramework/ReduxFramework/wiki/Arguments

             * */
            public function setArguments() {
                $theme = wp_get_theme(); // For use with some settings. Not necessary.
                $this->args = array(
                    // TYPICAL -> Change these values as you need/desire
                    'opt_name'           => 'hk_options',
                    // This is where your data is stored in the database and also becomes your global variable name.
                    'display_name'       => $theme->get( 'Name' ),
                    // Name that appears at the top of your panel
                    'display_version'    => $theme->get( 'Version' ),
                    // Version that appears at the top of your panel
                    'menu_type'          => 'menu',
                    //Specify if the admin menu should appear or not. Options: menu or submenu (Under appearance only)
                    'allow_sub_menu'     => true,
                    // Show the sections below the admin menu item or not
                    'menu_title'         => __( 'Tùy chỉnh', 'redux-framework-demo' ),
                    'page_title'         => __( 'Tùy chỉnh', 'redux-framework-demo' ),
                    // You will need to generate a Google API key to use this feature.
                    // Please visit: https://developers.google.com/fonts/docs/developer_api#Auth
                    'google_api_key'     => 'AIzaSyAs0iVWrG4E_1bG244-z4HRKJSkg7JVrVQ',
                    // Must be defined to add google fonts to the typography module
                    'async_typography'   => false,
                    // Use a asynchronous font on the front end or font string
                    'admin_bar'          => true,
                    // Show the panel pages on the admin bar
                    'global_variable'    => '',
                    // Set a different name for your global variable other than the opt_name
                    'dev_mode'           => false,
                    // Show the time the page took to load, etc
                    'customizer'         => true,
                    // Enable basic customizer support
                    // OPTIONAL -> Give you extra features
                    'page_priority'      => null,
                    // Order where the menu appears in the admin area. If there is any conflict, something will not show. Warning.
                    'page_parent'        => 'themes.php',
                    // For a full list of options, visit: http://codex.wordpress.org/Function_Reference/add_submenu_page#Parameters
                    'page_permissions'   => 'manage_options',
                    // Permissions needed to access the options panel.
                    'menu_icon'          => '',
                    // Specify a custom URL to an icon
                    'last_tab'           => '',
                    // Force your panel to always open to a specific tab (by id)
                    'page_icon'          => 'icon-themes',
                    // Icon displayed in the admin panel next to your menu_title
                    'page_slug'          => 'hk_options',
                    // Page slug used to denote the panel
                    'save_defaults'      => true,
                    // On load save the defaults to DB before user clicks save or not
                    'default_show'       => false,
                    // If true, shows the default value next to each field that is not the default value.
                    'default_mark'       => '',
                    // What to print by the field's title if the value shown is default. Suggested: *
                    'show_import_export' => true,
                    // Shows the Import/Export panel when not used as a field.
                    // CAREFUL -> These options are for advanced use only
                    'transient_time'     => 60 * MINUTE_IN_SECONDS,
                    'output'             => true,
                    // Global shut-off for dynamic CSS output by the framework. Will also disable google fonts output
                    'output_tag'         => true,
                    // Allows dynamic CSS to be generated for customizer and google fonts, but stops the dynamic CSS from going to the head
                    // 'footer_credit'     => '',                   // Disable the footer credit of Redux. Please leave if you can help it.
                    // FUTURE -> Not in use yet, but reserved or partially implemented. Use at your own risk.
                    'database'           => '',
                    // possible: options, theme_mods, theme_mods_expanded, transient. Not fully functional, warning!
                    'system_info'        => false,
                    // REMOVE
                    // HINTS
                    'hints'              => array(
                        'icon'          => 'icon-question-sign',
                        'icon_position' => 'right',
                        'icon_color'    => 'lightgray',
                        'icon_size'     => 'normal',
                        'tip_style'     => array(
                            'color'   => 'light',
                            'shadow'  => true,
                            'rounded' => false,
                            'style'   => '',
                        ),
                        'tip_position'  => array(
                            'my' => 'top left',
                            'at' => 'bottom right',
                        ),
                        'tip_effect'    => array(
                            'show' => array(
                                'effect'   => 'slide',
                                'duration' => '500',
                                'event'    => 'mouseover',
                            ),

                            'hide' => array(
                                'effect'   => 'slide',
                                'duration' => '500',
                                'event'    => 'click mouseleave',
                            ),
                        ),
                    )
                );
                // SOCIAL ICONS -> Setup custom links in the footer for quick links in your panel footer icons.

                $this->args['share_icons'][] = array(
                    'url'   => 'https://www.facebook.com/huykiradotnet',
                    'title' => 'Like us on Facebook',
                    'icon'  => 'el el-facebook'
                );

                $this->args['share_icons'][] = array(
                    'url'   => 'http://twitter.com/huykira',
                    'title' => 'Follow us on Twitter',
                    'icon'  => 'el el-twitter'
                );
                $this->args['share_icons'][] = array(
                    'url'   => 'http://www.linkedin.com/huykira',
                    'title' => 'Find us on LinkedIn',
                    'icon'  => 'el el-linkedin'
                );
                // Panel Intro text -> before the form
                if ( ! isset( $this->args['global_variable'] ) || $this->args['global_variable'] !== false ) {
                    if ( ! empty( $this->args['global_variable'] ) ) {
                        $v = $this->args['global_variable'];
                    } else {

                        $v = str_replace( '-', '_', $this->args['opt_name'] );

                    }
                    $this->args['intro_text'] = sprintf( __( '<p>Chào mừng bạn đến với khu vực tùy chỉnh website, Hãy cân nhắc kỹ trong quá trình tùy chỉnh! </p>', 'redux-framework-demo' ), $v );
                } else {

                    $this->args['intro_text'] = __( '<p>Chào mừng bạn đến với khu vực tùy chỉnh website, Hãy cân nhắc kỹ trong quá trình tùy chỉnh! </p>', 'redux-framework-demo' );
                }
            }
        }
        global $reduxConfig;
        $reduxConfig = new HuyKira_Theme_Options();
    }