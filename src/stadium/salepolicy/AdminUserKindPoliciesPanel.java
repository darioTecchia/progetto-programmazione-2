package stadium.salepolicy;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import stadium.structure.Structure;

/**
 * The Class AdminUserKindPoliciesPanel.
 */
@SuppressWarnings("serial")
public class AdminUserKindPoliciesPanel extends JPanel {

	private Structure structure;

	/**
	 * Instantiates a new admin user kind policies panel.
	 *
	 * @param structure the main structure
	 */
	public AdminUserKindPoliciesPanel(Structure structure) {
		this.structure = structure;
		initialize();
	}

	/**
	 * The Class PolicyItem.
	 */
	private class PolicyItem extends JPanel{

		private SalePolicy salePolicy;

		private JLabel policyName;
		private JLabel discountPercentage;
		private JCheckBox activePolicy;
		
		/**
		 * Instantiates a new policy item.
		 *
		 * @param salePolicy the sale policy
		 */
		public PolicyItem(SalePolicy salePolicy) {
			this.salePolicy = salePolicy;
			initialize();
		}

		/**
		 * Initialize GUI.
		 */
		private void initialize() {

			this.setLayout(new GridLayout(1, 3));
			this.setBorder(new TitledBorder(this.salePolicy.getKind().toString()));
			
			this.policyName = new JLabel(this.salePolicy.getKind().toString());
			this.add(this.policyName);
			
			this.discountPercentage = new JLabel(String.valueOf(this.salePolicy.getDiscountPercentage()));
			this.add(this.discountPercentage);
			
			this.activePolicy = new JCheckBox("", this.salePolicy.isActived());
			this.activePolicy.addActionListener(l -> {
				this.firePropertyChange("Policy Activated/Deactivated", !this.activePolicy.isSelected(), this.activePolicy.isSelected());
				this.salePolicy.setActived(this.activePolicy.isSelected());
			});
			this.add(this.activePolicy);
		}

	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new GridLayout(this.structure.getDiscountPolicies().size(), 1));

		for(SalePolicy salePolicy: this.structure.getDiscountPolicies()) {
			PolicyItem tempPolicyItem = new PolicyItem(salePolicy);
			tempPolicyItem.addPropertyChangeListener(l -> {
				if(l.getPropertyName().equalsIgnoreCase("Policy Activated/Deactivated")) {
					salePolicy.setActived((boolean) l.getNewValue());
					try {
						this.structure.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			this.add(tempPolicyItem);
		}

	}

}
