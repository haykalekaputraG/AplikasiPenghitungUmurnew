package aplikasipenghitungumurnew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class AplikasiPenghitungUmurnew extends JFrame {
    private JDateChooser dateChooser;
    private JButton btnHitung;
    private JTextField tfYears, tfMonths, tfDays;
    private JLabel lbNextBirthday;

    public AplikasiPenghitungUmurnew() {
        super("Aplikasi Penghitung Umur");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        btnHitung = new JButton("Hitung");

        tfYears = new JTextField(5);
        tfYears.setEditable(false);
        tfMonths = new JTextField(5);
        tfMonths.setEditable(false);
        tfDays = new JTextField(5);
        tfDays.setEditable(false);

        lbNextBirthday = new JLabel("Tanggal ulang tahun berikutnya: -");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0; c.gridy = 0; panel.add(new JLabel("Pilih tanggal lahir:"), c);
        c.gridx = 1; panel.add(dateChooser, c);

        c.gridx = 0; c.gridy = 1; panel.add(btnHitung, c);

        c.gridx = 0; c.gridy = 2; panel.add(new JLabel("Tahun:"), c);
        c.gridx = 1; panel.add(tfYears, c);

        c.gridx = 0; c.gridy = 3; panel.add(new JLabel("Bulan:"), c);
        c.gridx = 1; panel.add(tfMonths, c);

        c.gridx = 0; c.gridy = 4; panel.add(new JLabel("Hari:"), c);
        c.gridx = 1; panel.add(tfDays, c);

        c.gridx = 0; c.gridy = 5; c.gridwidth = 2; panel.add(lbNextBirthday, c);

        add(panel);

        btnHitung.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitungUmur();
            }
        });
    }

    private void hitungUmur() {
        Date selected = dateChooser.getDate();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih tanggal lahir dulu.");
            return;
        }

        LocalDate birth = selected.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        if (birth.isAfter(today)) {
            JOptionPane.showMessageDialog(this, "Tanggal lahir tidak boleh di masa depan.");
            return;
        }

        Period p = Period.between(birth, today);
        tfYears.setText(p.getYears() + "");
        tfMonths.setText(p.getMonths() + "");
        tfDays.setText(p.getDays() + "");

        LocalDate next = birth.withYear(today.getYear());
        if (!next.isAfter(today)) next = next.plusYears(1);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        lbNextBirthday.setText("Tanggal ulang tahun berikutnya: " + next.format(fmt));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplikasiPenghitungUmurnew().setVisible(true));
    }
}
