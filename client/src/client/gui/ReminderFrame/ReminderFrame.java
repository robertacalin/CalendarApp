package client.gui.ReminderFrame;

import client.controller.ReminderController;
import lib.dto.EventDto;
import lib.dto.ReminderDto;
import org.jdatepicker.DateLabelFormatter;
import org.jdatepicker.UtilDateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;

public class ReminderFrame extends JFrame{
    private JPanel panel1;
    private JList list1;
    private JLabel label;
    private JButton adaugaReminderButton;
    private JButton stergeReminderButton;
    private JPanel dateReminderPanel;
    private JSpinner spinner1;
    private EventDto eveniment;
    private JDatePickerImpl myDatePicker;

    private DefaultListModel<ReminderDto> model = new DefaultListModel<>();

    public ReminderFrame(EventDto eveniment) {

        this.eveniment = eveniment;
        setContentPane(panel1);
        setTitle("Remindere eveniment " + eveniment.getName());
        setSize(400,700);
        setLocationRelativeTo(null);
        setVisible(true);

        label.setText(eveniment.toString());

        list1.setModel(model);

        var reminders = ReminderController.getInstance().findByEvenimentId(eveniment.getId());

        model.clear();

        reminders.forEach(model::addElement);

        adaugaReminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var reminderTime = LocalTime.ofInstant(((SpinnerDateModel)spinner1.getModel())
                        .getDate().toInstant(), ZoneId.systemDefault());

                var year = myDatePicker.getModel().getYear();
                var month = myDatePicker.getModel().getMonth() + 1;
                var day = myDatePicker.getModel().getDay();
                LocalDate reminderDate = LocalDate.of(year, month, day);

                if (reminderDate.isAfter(eveniment.getEventDate()) ) {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel,
                            "Reminderul unui eveniment trebuie sa fie inaintea acestuia",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                } else if (reminderDate.isEqual(eveniment.getEventDate()) &&
                        (reminderTime.isAfter(eveniment.getEventHour()) || reminderTime.equals(eveniment.getEventHour()))) {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel,
                            "Reminderul unui eveniment trebuie sa fie inaintea acestuia",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                }  else {
                    var reminderDto = new ReminderDto(
                            reminderDate,
                            reminderTime,
                            eveniment.getId(),
                            false
                    );

                    ReminderController.getInstance().persist(reminderDto);

                    refresh();
                }
            }
        });

        stergeReminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var reminder = (ReminderDto) list1.getSelectedValue();

                if(reminder != null) {
                    ReminderController.getInstance().deleteById(reminder.getId());
                    refresh();
                } else {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel,
                            "Trebuie sa selectati un reminder",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() {
        myDatePicker = this.createNewDatePicker();
        dateReminderPanel = new JPanel();
        dateReminderPanel.add(myDatePicker);

        this.createTimePicker();
    }

    private JDatePickerImpl createNewDatePicker() {
        UtilDateModel model = new UtilDateModel();
        model.setValue(new Date());

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private void createTimePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        SpinnerDateModel spinnerModel = new SpinnerDateModel();
        spinnerModel.setValue(calendar.getTime());

        this.spinner1 = new JSpinner(spinnerModel);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(this.spinner1, "HH:mm:00");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);

        this.spinner1.setEditor(editor);
    }

    private void refresh() {
        var reminders = ReminderController.getInstance().findByEvenimentId(eveniment.getId());

        model.clear();

        var remindersOrdered = reminders.stream().sorted(Comparator.comparing(ReminderDto::getReminderHour));

        remindersOrdered.forEach(model::addElement);
    }
}
