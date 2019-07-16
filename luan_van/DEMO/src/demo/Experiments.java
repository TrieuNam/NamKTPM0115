
package demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import demo.AbstractDynamicAccessControl.PolicyChangeType;
import demo.AbstractDynamicAccessControl.RequestInputFormat;

public class Experiments {

	public static void main(String[] args) throws Exception {
//		List<String> requestList = new ArrayList<>();
//		String folder = "src/test/resources/testsets/healthcare/xml-requests";
//		File file = new File(folder);
//
//		for (File child : file.listFiles()) {
//			StringBuilder sb = new StringBuilder();
//			BufferedReader br = new BufferedReader(new FileReader(child));
//			for (Iterator<String> itor = br.lines().iterator(); itor.hasNext();) {
//				String line = itor.next();
//				sb.append(line).append("\n");
//			}
//			requestList.add(sb.toString());
//			br.close();
//		}
		
		BalanaExperiment.COUNT_FIRST_EVALUATION = Boolean.valueOf(args[0]);
		
		BalanaExperiment.HANDLE_POLICY_CHANGE_TURN_ON = Boolean.valueOf(args[1]);
		
		PolicyChangeType changeType = PolicyChangeType.valueOf(args[2]);
		
		smokeTestSimpleRequest();

		switch (changeType) {
		case DELETE_POLICY:
			testDeletePolicy();
			break;
		case INSERT_POLICY:
			testInsertPolicy();
			break;
		case UPDATE_POLICY_DELETE_CONDITION:
			testDeleteCondition();
			break;
		case UPDATE_POLICY_EDIT_CONDITION:
			testEditCondition();
			break;
		case UPDATE_POLICY_INSERT_CONDITION:
			testInsertCondition();
			break;
		default:
			break;
		}
		
		// testDeletePolicy();
		// testInsertPolicy();
		// testDeleteCondition();
		// testEditCondition();
		// testInsertCondition();
		
	}
	
	private static String readRequest(final String filename) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> itor = br.lines().iterator(); itor.hasNext();) {
			String line = itor.next();
			sb.append(line).append("\n");
		}
		br.close();
		String requestStr = sb.toString();
		return requestStr;
	}

	private static void smokeTestSimpleRequest() throws Exception {
		BalanaExperiment balanaExperiment = new BalanaExperiment() {

			@Override
			protected PolicyChangeType policyChangedOnce(Object response) {
				return null;
			}

			@Override
			protected Object handlePolicyChange(Object request, Object response) throws Exception {
				return response;
			}

		};
		balanaExperiment.evaluate(readRequest("experiments/TestSimpleRequest/Request.1.xml"), RequestInputFormat.XML);
	}

	private static void testDeletePolicy() throws Exception {
		final String newPolicy = "experiments/TestDeletePolicy/health_care_after_delete.healthCarePsPatientRecord.xml";
		BalanaExperiment balanaExperiment = new BalanaExperiment() {

			@Override
			protected PolicyChangeType policyChangedOnce(Object response) {
				
				updatePolicies(newPolicy, false);

				return PolicyChangeType.DELETE_POLICY;
			}

			@Override
			protected Object handlePolicyChange(Object request, Object response) throws Exception {
				updatePolicies(getPolicyShort(newPolicy), true);

				long start = System.nanoTime();
				Object ret = evaluateRequest(request);
				long endTime = System.nanoTime();
				sum += (endTime - start);
				addAction(ProcessAction.EvaluateRequest);
				return ret;
			}
			
		};
		Object response = balanaExperiment.evaluate(readRequest("experiments/TestDeletePolicy/Request.7.xml"), RequestInputFormat.XML);
		balanaExperiment.printExperiment(response);
	}
	
	private static void testInsertPolicy() throws Exception {
		BalanaExperiment balanaExperiment = new BalanaExperiment() {

			@Override
			protected PolicyChangeType policyChangedOnce(Object response) {
				updatePolicies( "experiments/TestInsertPolicy/health_care_after_insert.healthCarePsPatientRecord.xml", false);
				
				return PolicyChangeType.INSERT_POLICY;
			}

			@Override
			protected Object handlePolicyChange(Object request, Object response) {
				long start = System.nanoTime();
				Object ret = evaluateRequest(request);
				long endTime = System.nanoTime();
				sum += (endTime - start);
				addAction(ProcessAction.EvaluateRequest);
				return ret;
			}
			
		};
		
		Object response = balanaExperiment.evaluate(readRequest("experiments/TestInsertPolicy/Request.5.xml"), RequestInputFormat.XML);
		balanaExperiment.printExperiment(response);
	}
	
	private static void testDeleteCondition() throws Exception {
		final String newPolicy = "experiments/TestDeleteCondition/health_care_update_delete_rule.healthCarePsPatientRecord.xml";
		BalanaExperiment balanaExperiment = new BalanaExperiment() {

			@Override
			protected PolicyChangeType policyChangedOnce(Object response) {
				updatePolicies( newPolicy, false );
				// There's only one policy left for evaluate
				
				return PolicyChangeType.UPDATE_POLICY_DELETE_CONDITION;
			}

			@Override
			protected Object handlePolicyChange(Object request, Object response) {
				updatePolicies(getPolicyShort(newPolicy), true);
				long start = System.nanoTime();
				Object ret = evaluateRequest(request);
				long endTime = System.nanoTime();
				sum += (endTime - start);
				addAction(ProcessAction.EvaluateRequest);
				return ret;
			}
			
		};
		
		Object response = balanaExperiment.evaluate(readRequest("experiments/TestDeleteCondition/Request.11.xml"), RequestInputFormat.XML);
		balanaExperiment.printExperiment(response);
	}
	
	private static void testEditCondition() throws Exception {
		final String newPolicy = "experiments/TestEditCondition/health_care_update_edit_condition.healthCarePsPatientRecord.xml";
		BalanaExperiment balanaExperiment = new BalanaExperiment() {

			@Override
			protected PolicyChangeType policyChangedOnce(Object response) {
				updatePolicies( newPolicy, false);
				// There's only one policy left for evaluate
				
				return PolicyChangeType.UPDATE_POLICY_EDIT_CONDITION;
			}

			@Override
			protected Object handlePolicyChange(Object request, Object response) {
				updatePolicies(getPolicyShort(newPolicy), true);
				long start = System.nanoTime();
				Object ret = evaluateRequest(request);
				long endTime = System.nanoTime();
				sum += (endTime - start);
				addAction(ProcessAction.EvaluateRequest);
				return ret;
			}
			
		};
		
		Object response = balanaExperiment.evaluate(readRequest("experiments/TestEditCondition/Request.12.xml"), RequestInputFormat.XML);
		balanaExperiment.printExperiment(response);
	}
	
	private static void testInsertCondition() throws Exception {
		final String newPolicy = "experiments/TestInsertCondition/health_care_update_insert_condition.healthCarePsPatientRecord.xml";
		BalanaExperiment balanaExperiment = new BalanaExperiment() {

			@Override
			protected PolicyChangeType policyChangedOnce(Object response) {
				updatePolicies(newPolicy, false);
				// There's only one policy left for evaluate
				
				return PolicyChangeType.UPDATE_POLICY_INSERT_CONDITION;
			}

			@Override
			protected Object handlePolicyChange(Object request, Object response) throws Exception {
				updatePolicies(getPolicyShort(newPolicy), true);
				
				Object newRequest = rewrite(request);
				long start = System.nanoTime();
				Object ret = evaluateRequest(newRequest);
				long endTime = System.nanoTime();
				sum += (endTime - start);
				addAction(ProcessAction.EvaluateRequest);
				return ret;
			}
			
			protected Object rewrite(Object request) throws FileNotFoundException, IOException {
				Object newRequest = parseRequest(readRequest("experiments/TestInsertCondition/Request.13.xml"), RequestInputFormat.XML);
				addAction(ProcessAction.RewriteRequest);
				return newRequest;
			}

			
		};
		
		Object response = balanaExperiment.evaluate(readRequest("experiments/TestInsertCondition/Request.5.xml"), RequestInputFormat.XML);
		balanaExperiment.printExperiment(response);
	}

	private static String getPolicyShort(String newPolicy) {
		return newPolicy.replace(".xml", "-short.xml");
	}
}
