package experiments;

import com.microsoft.z3.Context;

import policy.SMTPolicy;
import policy.SMTRule;
import policy.SMTPolicyElement.Decision;
import query.QueryRunner;
import utils.PresentationUtils;

public class QueryTest extends AbstractExperiments{
	
	private static final String DOMAIN = "( ( user-id = Doctor-Patient "
											+ "\\VEE user-id = Nurse-Patient "
											+ "\\VEE user-id = Doctor-Emer-Agreement "
											+ "\\VEE user-id = Doctor-Non-Patient "
											+ "\\VEE user-id = Nurse-Non-Patient ) "
											+ "\\WEDGE ( role = Doctor \\VEE role = Nurse \\VEE role = External ) "
											+ "\\WEDGE ( party-type = Pharmacy \\VEE party-type = Insurance ) "
											+ "\\WEDGE ( resource-type = Patient.Record \\VEE resource-type = Patient.Record.Billing \\VEE resource-type = Patient.Record.Medicines ) "
											+ "\\WEDGE ( patient:management-info:status = NotAvailableDoctorNurse \\VEE patient:management-info:status = AvailableDoctorNurse ) "
											+ "\\WEDGE patient:management-info:relative-passport-id = Patient-Relative "
											+ "\\WEDGE ( patient:management-info:conscious-status = Conscious \\VEE patient:management-info:conscious-status = Unconscious ) "
											+ "\\WEDGE patient:record:department = Heart "
											+ "\\WEDGE patient:record:assigned-doctor-id = Doctor-Patient "
											+ "\\WEDGE patient:record:assigned-nurse-id = Doctor-Nurse "
											+ "\\WEDGE patient:emgergency-agreement:doctor = Doctor-Emer-Agreement "
											+ "\\WEDGE patient:emgergency-agreement:relative-passport-id = Patient-Relative "
											+ "\\WEDGE action-type = Read "
											+ "\\WEDGE location = Heart )";
	
	public static void main(String[] args) {
		QueryTest qOnPolicy = new QueryTest();
		QueryRunner qRunner =new QueryRunner();
		
		QueryRunner.SHOW_AT_POLICY_ONLY = true;
		QueryRunner.SHOW_AT_POLICY_WITH_TARGET_COMBINATION = false;
		QueryRunner.SHOW_AT_RULE = false;
		
		qOnPolicy.apply(qRunner, !(QueryRunner.SHOW_AT_POLICY_ONLY || QueryRunner.SHOW_AT_POLICY_WITH_TARGET_COMBINATION || QueryRunner.SHOW_AT_RULE));
	}
	
	private void apply(QueryRunner qRunner, boolean showAtCondtraint) {
		SMTPolicy[] policyFormulas = qRunner.loadPolicies(new String[] { "policies\\health-care.xml" }, true, QueryRunner.xacmlVersion, DOMAIN);
		
		System.out.println("\n\n--------------------------------------- EXPERIMENTS -----------------------------------------\n\n");
		SMTRule smtRule = policyFormulas[0].getFinalElement();
		
		Context context = qRunner.getLoader().getContext();
		if (showAtCondtraint) {
			System.out.println(Decision.Permit + " " + PresentationUtils.normalUniform(smtRule.getPermitDS()));
			System.out.println(Decision.Deny + " " + PresentationUtils.normalUniform(smtRule.getDenyDS()));
			System.out.println(Decision.Indet + " " + PresentationUtils.normalUniform(smtRule.getIndeterminateDS(context)));
			System.out.println(Decision.Na + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Na, true)));
		}
		else {
			System.out.println(Decision.Permit + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Permit, true)));
			System.out.println(Decision.Deny + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Deny, true)));
			System.out.println(Decision.Indet + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Indet, true)));
			System.out.println(Decision.Na + " " + PresentationUtils.normalUniform(smtRule.getDSStr(Decision.Na, true)));
		}
	}
}
