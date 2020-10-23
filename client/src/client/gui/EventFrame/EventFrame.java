package client.gui.EventFrame;

import client.controller.AccountController;
import client.controller.EventController;
import client.controller.ReminderController;
import client.gui.LoginFrame.LoginFrame;
import client.gui.ReminderFrame.ReminderFrame;
import lib.dto.AccountDto;
import lib.dto.EventDto;
import lib.dto.ReminderDto;
import org.jdatepicker.DateLabelFormatter;
import org.jdatepicker.UtilDateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventFrame extends JFrame{

    private final ScheduledExecutorService service;
    private JPanel panel1;
    private JButton previousButton;
    private JTextField textField1;
    private JButton nextButton;
    private JList list1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton addEventButton;
    private JButton deleteEventButton;
    private JPanel eventDatePanel;
    private JSpinner spinner1;
    private JPanel currentDatePanel;
    private JDatePickerImpl currentDatePicker;
    private JDatePickerImpl eventDatePicker;

    private DefaultListModel<EventDto> model = new DefaultListModel<>();
    private AccountDto accountDto;

    public EventFrame(AccountDto accountDto) {
        service = Executors.newSingleThreadScheduledExecutor();
        this.accountDto = accountDto;
        setTitle("Calendar " + accountDto.getUsername());
        setContentPane(panel1);
        setSize(400,600);
        setLocationRelativeTo(null);
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initMenu();

        list1.setModel(model);

        var task = (Runnable) () -> {
            try {
                Collection<ReminderDto> unseenReminders = ReminderController.getInstance().getUnseenReminders(accountDto);

                for(ReminderDto reminder : unseenReminders) {
                    var unseenReminderString = reminder.toString();
                    if (shouldActivate(reminder)) {
                        JPanel panel = new JPanel();
                        JOptionPane.showMessageDialog(panel,
                                unseenReminderString,
                                "Reminder " + accountDto.getUsername(),
                                JOptionPane.INFORMATION_MESSAGE);

                        ReminderController.getInstance().update(reminder);
                    }
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        };
        service.scheduleWithFixedDelay(task, 0,10, TimeUnit.SECONDS);

        refresh();

        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var eventTime = LocalTime.ofInstant(((SpinnerDateModel)spinner1.getModel())
                        .getDate().toInstant(), ZoneId.systemDefault());

                var year = eventDatePicker.getModel().getYear();
                var month = eventDatePicker.getModel().getMonth() + 1;
                var day = eventDatePicker.getModel().getDay();
                LocalDate eventDate = LocalDate.of(year, month, day);

                if(!textField2.getText().equals("")) {
                    var eventDto = new EventDto(
                            textField2.getText(),
                            eventDate,
                            eventTime,
                            accountDto.getId()
                    );

                    EventController.getInstance().persist(eventDto);

                    textField2.setText("");

                    refresh();
                } else {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel,
                            "Evenimentul trebuie sa aiba un nume",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentDatePicker.getModel().addDay(-1);

               refresh();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentDatePicker.getModel().addDay(1);

                refresh();
            }
        });

        deleteEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var event = (EventDto) list1.getSelectedValue();

                if(event != null) {
                    EventController.getInstance().deleteById(event.getId());
                    refresh();
                } else {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel,
                            "Trebuie sa selectati un eveniment",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EventDto selected = (EventDto) list1.getSelectedValue();

                if (selected != null && e.getClickCount() == 2) {
                    new ReminderFrame(selected);
                }
            }
        });

        currentDatePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
    }

    private void refresh() {
        var year = currentDatePicker.getModel().getYear();
        var month = currentDatePicker.getModel().getMonth() + 1;
        var day = currentDatePicker.getModel().getDay();
        LocalDate currentDate = LocalDate.of(year, month, day);

        var events = EventController.getInstance().findEventsByContIdAndDate(accountDto.getId(), currentDate);

        var eventsOrdered = events.stream().sorted(Comparator.comparing(EventDto::getEventHour));

        model.clear();

        eventsOrdered.forEach(model::addElement);
    }

    public void initMenu() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem logOutItem, deleteItem;

        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        logOutItem = new JMenuItem("Log Out");
        deleteItem = new JMenuItem("Delete Account");

        logOutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                new LoginFrame();
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                AccountController.getInstance().deleteById(accountDto.getId());
                new LoginFrame();
            }
        });

        menu.add(logOutItem);
        menu.add(deleteItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

    }

    private void createUIComponents() {
        currentDatePicker = this.createNewDatePicker();
        currentDatePanel = new JPanel();
        currentDatePanel.add(currentDatePicker);

        eventDatePicker = this.createNewDatePicker();
        eventDatePanel = new JPanel();
        eventDatePanel.add(eventDatePicker);

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

    boolean shouldActivate(ReminderDto reminder) {
        if (reminder.getReminderDate().isBefore(LocalDate.now())) {
            return true;
        } else if (reminder.getReminderDate().isEqual(LocalDate.now()) && reminder.getReminderHour().isBefore(LocalTime.now().withSecond(0))) {
            return true;
        }
        return false;
    }
}
