DO $$
BEGIN

IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname='osgp_core_db_api_iec61850_user' )

THEN
	CREATE USER osgp_core_db_api_iec61850_user PASSWORD '1234' NOSUPERUSER;
	GRANT SELECT ON public.ssld TO osgp_core_db_api_iec61850_user;
	GRANT SELECT ON public.device TO osgp_core_db_api_iec61850_user;
	GRANT SELECT ON public.device_output_setting TO osgp_core_db_api_iec61850_user;

END IF;

END;
$$
