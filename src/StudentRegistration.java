import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRegistration extends JFrame implements ActionListener {

    // Labels
    JLabel lblName, lblRoll, lblGender, lblBranch;

    // Text Fields
    JTextField txtName, txtRoll, txtBranch;

    // Radio Buttons
    JRadioButton male, female;
    ButtonGroup genderGroup;

    // Check Box
    JCheckBox terms;

    // Buttons
    JButton submit, reset;

    // MySQL Database Details
    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "root";


    // Constructor
    StudentRegistration() {

        setTitle("Student Registration Form");
        setSize(450, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Student Name
        lblName = new JLabel("Student Name");
        lblName.setBounds(30, 30, 120, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(170, 30, 180, 30);
        add(txtName);


        // Roll Number
        lblRoll = new JLabel("Roll Number");
        lblRoll.setBounds(30, 80, 120, 30);
        add(lblRoll);

        txtRoll = new JTextField();
        txtRoll.setBounds(170, 80, 180, 30);
        add(txtRoll);


        // Gender
        lblGender = new JLabel("Gender");
        lblGender.setBounds(30, 130, 120, 30);
        add(lblGender);

        male = new JRadioButton("Male");
        male.setBounds(170, 130, 80, 30);
        add(male);

        female = new JRadioButton("Female");
        female.setBounds(260, 130, 100, 30);
        add(female);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);


        // Branch
        lblBranch = new JLabel("Branch");
        lblBranch.setBounds(30, 180, 120, 30);
        add(lblBranch);

        txtBranch = new JTextField();
        txtBranch.setBounds(170, 180, 180, 30);
        add(txtBranch);


        // Terms & Conditions
        terms = new JCheckBox("I accept Terms & Conditions");
        terms.setBounds(30, 230, 250, 30);
        add(terms);


        // Submit Button
        submit = new JButton("Submit");
        submit.setBounds(80, 290, 100, 35);
        submit.addActionListener(this);
        add(submit);


        // Reset Button
        reset = new JButton("Reset");
        reset.setBounds(220, 290, 100, 35);
        reset.addActionListener(this);
        add(reset);


        setVisible(true);
    }


    // Database Connection
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }


    // Button Actions
    @Override
    public void actionPerformed(ActionEvent e) {

        // Reset Button
        if (e.getSource() == reset) {

            txtName.setText("");
            txtRoll.setText("");
            txtBranch.setText("");

            genderGroup.clearSelection();
            terms.setSelected(false);

            return;
        }


        // Submit Button

        // Validate Name
        if (txtName.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Student Name"
            );

            return;
        }


        // Validate Roll Number
        if (txtRoll.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Roll Number"
            );

            return;
        }


        // Validate Gender
        if (!male.isSelected() && !female.isSelected()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select Gender"
            );

            return;
        }


        // Validate Branch
        if (txtBranch.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Branch"
            );

            return;
        }


        // Validate Terms
        if (!terms.isSelected()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please accept Terms & Conditions"
            );

            return;
        }


        // Get Gender
        String gender;

        if (male.isSelected()) {
            gender = "Male";
        } else {
            gender = "Female";
        }


        // Get Form Data
        String name = txtName.getText().trim();
        String rollNo = txtRoll.getText().trim();
        String branch = txtBranch.getText().trim();


        // SQL Query
        String sql =
                "INSERT INTO student (name, roll_no, gender, branch) "
                + "VALUES (?, ?, ?, ?)";


        // Connect to MySQL and Insert Data
        try {

            Connection con = getConnection();

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, name);
            pst.setString(2, rollNo);
            pst.setString(3, gender);
            pst.setString(4, branch);

            int rows = pst.executeUpdate();


            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration Successful!\n\n"
                        + "Name : " + name
                        + "\nRoll Number : " + rollNo
                        + "\nGender : " + gender
                        + "\nBranch : " + branch
                );


                // Clear form after successful registration
                txtName.setText("");
                txtRoll.setText("");
                txtBranch.setText("");

                genderGroup.clearSelection();
                terms.setSelected(false);
            }


            pst.close();
            con.close();


        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Connection Error!\n\n"
                    + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }


    // Main Method
    public static void main(String[] args) {

        new StudentRegistration();
    }
}