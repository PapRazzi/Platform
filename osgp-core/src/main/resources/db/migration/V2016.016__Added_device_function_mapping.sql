CREATE TABLE device_function_mapping
(
  function_group character varying(255) NOT NULL,
  function character varying(255) NOT NULL,
  CONSTRAINT device_function_mapping_pkey PRIMARY KEY (function_group, function)
);

ALTER TABLE public.device_function_mapping OWNER TO osp_admin;
GRANT ALL ON public.device_function_mapping TO osp_admin;
GRANT SELECT ON public.device_function_mapping TO osgp_read_only_ws_user;

INSERT INTO device_function_mapping(function_group, function) VALUES
  ('OWNER', 'START_SELF_TEST'),
  ('OWNER', 'STOP_SELF_TEST'),
  ('OWNER', 'SET_LIGHT'),
  ('OWNER', 'GET_DEVICE_AUTHORIZATION'),
  ('OWNER', 'SET_EVENT_NOTIFICATIONS'),
  ('OWNER', 'SET_DEVICE_AUTHORIZATION'),
  ('OWNER', 'GET_EVENT_NOTIFICATIONS'),
  ('OWNER', 'UPDATE_FIRMWARE'),
  ('OWNER', 'GET_FIRMWARE_VERSION'),
  ('OWNER', 'SET_TARIFF_SCHEDULE'),
  ('OWNER', 'SET_LIGHT_SCHEDULE'),
  ('OWNER', 'SET_CONFIGURATION'),
  ('OWNER', 'GET_CONFIGURATION'),
  ('OWNER', 'GET_STATUS'),
  ('OWNER', 'GET_LIGHT_STATUS'),
  ('OWNER', 'GET_TARIFF_STATUS'),
  ('OWNER', 'REMOVE_DEVICE'),
  ('OWNER', 'GET_ACTUAL_POWER_USAGE'),
  ('OWNER', 'GET_POWER_USAGE_HISTORY'),
  ('OWNER', 'RESUME_SCHEDULE'),
  ('OWNER', 'SET_REBOOT'),
  ('OWNER', 'SET_TRANSITION'),
  ('OWNER', 'UPDATE_KEY'),
  ('OWNER', 'REVOKE_KEY'),
  ('OWNER', 'FIND_SCHEDULED_TASKS'),
  ('OWNER', 'ADD_METER'),
  ('OWNER', 'FIND_EVENTS'),
  ('OWNER', 'REQUEST_PERIODIC_METER_DATA'),
  ('OWNER', 'SYNCHRONIZE_TIME'),
  ('OWNER', 'REQUEST_SPECIAL_DAYS'),
  ('OWNER', 'SET_ALARM_NOTIFICATIONS'),
  ('OWNER', 'SET_CONFIGURATION_OBJECT'),
  ('OWNER', 'SET_ADMINISTRATIVE_STATUS'),
  ('OWNER', 'GET_ADMINISTRATIVE_STATUS'),
  ('OWNER', 'SET_ACTIVITY_CALENDAR'),
  ('OWNER', 'REQUEST_ACTUAL_METER_DATA'),
  ('OWNER', 'READ_ALARM_REGISTER'),
  ('OWNER', 'SEND_WAKEUP_SMS'),
  ('OWNER', 'GET_SMS_DETAILS'),
  ('OWNER', 'SET_ENCRYPTION_KEY_EXCHANGE_ON_G_METER'),
  ('OWNER', 'REPLACE_KEYS'),
  ('OWNER', 'SET_PUSH_SETUP_ALARM'),
  ('OWNER', 'SET_PUSH_SETUP_SMS'),
  ('OWNER', 'GET_CONFIGURATION_OBJECTS'),
  ('OWNER', 'SWITCH_CONFIGURATION_BANK'),
  ('OWNER', 'SWITCH_FIRMWARE'),
  ('OWNER', 'UPDATE_DEVICE_SSL_CERTIFICATION'),
  ('OWNER', 'SET_DEVICE_VERIFICATION_KEY'),
  ('INSTALLATION', 'START_SELF_TEST'),
  ('INSTALLATION', 'STOP_SELF_TEST'),
  ('INSTALLATION', 'GET_DEVICE_AUTHORIZATION'),
  ('INSTALLATION', 'ADD_METER'),
  ('AD_HOC', 'SET_LIGHT'),
  ('AD_HOC', 'GET_DEVICE_AUTHORIZATION'),
  ('AD_HOC', 'GET_STATUS'),
  ('AD_HOC', 'GET_LIGHT_STATUS'),
  ('AD_HOC', 'GET_TARIFF_STATUS'),
  ('AD_HOC', 'RESUME_SCHEDULE'),
  ('AD_HOC', 'SET_REBOOT'),
  ('AD_HOC', 'SET_TRANSITION'),
  ('MANAGEMENT', 'GET_DEVICE_AUTHORIZATION'),
  ('MANAGEMENT', 'SET_EVENT_NOTIFICATIONS'),
  ('MANAGEMENT', 'GET_EVENT_NOTIFICATIONS'),
  ('MANAGEMENT', 'REMOVE_DEVICE'),
  ('MANAGEMENT', 'UPDATE_KEY'),
  ('MANAGEMENT', 'REVOKE_KEY'),
  ('MANAGEMENT', 'UPDATE_DEVICE_SSL_CERTIFICATION'),
  ('MANAGEMENT', 'SET_DEVICE_VERIFICATION_KEY'),
  ('FIRMWARE', 'GET_DEVICE_AUTHORIZATION'),
  ('FIRMWARE', 'UPDATE_FIRMWARE'),
  ('FIRMWARE', 'GET_FIRMWARE_VERSION'),
  ('FIRMWARE', 'SWITCH_FIRMWARE'),
  ('SCHEDULING', 'GET_DEVICE_AUTHORIZATION'),
  ('SCHEDULING', 'SET_LIGHT_SCHEDULE'),
  ('TARIFF_SCHEDULING', 'GET_DEVICE_AUTHORIZATION'),
  ('TARIFF_SCHEDULING', 'SET_TARIFF_SCHEDULE'),
  ('CONFIGURATION', 'GET_DEVICE_AUTHORIZATION'),
  ('CONFIGURATION', 'SET_CONFIGURATION'),
  ('CONFIGURATION', 'GET_CONFIGURATION'),
  ('CONFIGURATION', 'SWITCH_CONFIGURATION_BANK'),
  ('MONITORING', 'GET_DEVICE_AUTHORIZATION'),
  ('MONITORING', 'GET_ACTUAL_POWER_USAGE'),
  ('MONITORING', 'GET_POWER_USAGE_HISTORY');