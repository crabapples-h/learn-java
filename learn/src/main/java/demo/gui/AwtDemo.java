package demo.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AwtDemo {
    private static final int x = 400;
//    private static final int x = 1300;
    private static final int y = 400;
    private static final int width = 400;
    private static final int height = 500;
    private static int type = 0;
    private static Timer timer = null;
    private static final Dimension DIMENSION = new Dimension(AwtDemo.width,AwtDemo.height);

    public static void main(String[] args) throws IOException {
//        demo1();
//        demo2();
//        demo3();
//        demo4();
//        demo5();
//        demo6();
//        demo7();
//        demo8();
//        demo9();
//        demo10();
//        demo11();
//        demo12();
//        demo13();
//        demo14();
//        demo15();
//        demo16();
//        demo17();
//        demo18();
//        demo19();
//        demo20();
//        demo21();
        demo22();
    }
    static class Demo22Canvas extends Canvas{
        private BufferedImage bufferedImage;
        public void setBufferedImage(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
        }

        public BufferedImage getBufferedImage() {
            return bufferedImage;
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(bufferedImage,0,0,null);
        }
    }
    /**
     * ImageIO
     */
    static void demo22() {
        Frame frame = createBaseWindow("ImageIO");
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("文件");
        String [] menus = {"打开","保存"};
        FileDialog dialog = new FileDialog(frame);
        Demo22Canvas canvas = new Demo22Canvas();
        for (String s : menus) {
            MenuItem item = new MenuItem(s);
            item.addActionListener(e->{
                try {
                    String cmd = e.getActionCommand();
                    String filePath;
                    switch (cmd){
                    case "打开":
                        dialog.setMode(FileDialog.LOAD);
                        dialog.setVisible(true);
                        filePath = dialog.getDirectory()+dialog.getFile();
                        BufferedImage bufferedImage =ImageIO.read(new File(filePath));
                        canvas.setBufferedImage(bufferedImage);
                        int width = bufferedImage.getWidth();
                        int height = bufferedImage.getHeight();
                        canvas.setPreferredSize(new Dimension(width,height));
                        canvas.repaint();
                        frame.pack();
                        break;
                    case "保存":
                        dialog.setMode(FileDialog.SAVE);
                        dialog.setVisible(true);
                        filePath = dialog.getDirectory()+dialog.getFile();
                        BufferedImage image = canvas.getBufferedImage();
                        ImageIO.write(image,"png",new File(filePath));
                    break;
                }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            menu.add(item);
        }
        menuBar.add(menu);
        frame.setMenuBar(menuBar);
        frame.add(canvas);
    }
    static class Demo21Canvas extends Canvas{
        private final Image image;
        Demo21Canvas(Image image){
            this.image = image;
        }
        @Override
        public void paint(Graphics g) {
            g.drawImage(image,0,0,null);
        }
    }
    /**
     * BufferedImage
     */
    static void demo21() {
        int [] location = {-1,-1};
        AtomicReference<Color> color = new AtomicReference<>(Color.black);
        Frame frame = createBaseWindow("BufferedImage");
        PopupMenu menus = new PopupMenu();
        String[] colorsItem = {"红色","蓝色","绿色"};
        for (String str : colorsItem) {
            MenuItem item = new MenuItem(str);
            item.addActionListener(e->{
                String command = e.getActionCommand();
                switch (command){
                    case "红色" : color.set(Color.red);break;
                    case "蓝色" : color.set(Color.blue);break;
                    case "绿色" : color.set(Color.green);break;
                }
            });
            menus.add(item);
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        Demo21Canvas imageCanvas = new Demo21Canvas(image);
        imageCanvas.setPreferredSize(DIMENSION);
        graphics.setColor(Color.white);
        graphics.fillRect(0,0,AwtDemo.width,AwtDemo.height);
        imageCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()){
                    menus.show(frame,e.getX(),e.getY());
                }
                location[0]= -1;
                location[1]= -1;
            }
        });
        imageCanvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(location[0]+location[1]>0){
                    graphics.drawLine(location[0],location[1],e.getX(),e.getY());
                }
                graphics.setColor(color.get());
                imageCanvas.repaint();
                location[0]= e.getX();
                location[1]= e.getY();
            }
        });

        frame.add(imageCanvas);
        frame.add(menus);
        frame.pack();
    }
    static class Demo20BallCanvas extends Canvas {
        private int x = 0;
        private int y = 0;
        private boolean isOver = false;

        private final int round;

        Demo20BallCanvas(Dimension dimension,int round) {
            this.setPreferredSize(dimension);
            this.round = round;
        }

        public void reSet(int x, int y, AtomicBoolean isOver) {
            this.x = x;
            this.y = y;
            this.isOver = isOver.get();
        }

        @Override
        public void paint(Graphics g) {
            if(isOver){
                g.setColor(Color.red);
                int x = AwtDemo.width/2;
                int y = AwtDemo.height/2;
                g.drawString("游戏结束", x, y);
            }else {
                g.setColor(Color.red);
                g.drawOval(x, y, round, round);
                g.fillOval(x, y, round, round);
                g.setColor(Color.blue);
            }
        }
    }
    static class Demo20RacketCanvas extends Canvas {
        private int x = 0;
        private final int width;

        Demo20RacketCanvas(Dimension dimension,int width) {
            this.setPreferredSize(dimension);
            this.width = width;
        }

        public void reSet(int x) {
            this.x = x;
        }

        @Override
        public void paint(Graphics g) {
                int height = 10;
                int y = 0;
                g.setColor(Color.red);
                g.drawRect(x, y, width, height);
                g.fillRect(x, y, width, height);
                g.setColor(Color.black);
        }
    }
    /**
     * 弹球小游戏演示
     */
    static void demo20() {
        AtomicInteger scope = new AtomicInteger();
        int ballRound = 10;
        int racketWidth = 50;
        int ballHeight = 400;
        AtomicBoolean isOver = new AtomicBoolean(false);
        AtomicBoolean isPlay = new AtomicBoolean(false);
        boolean[] isDown = {true, true};
        int[] location = {0, 0, 0};
        Frame frame = createBaseWindow("弹球小游戏演示");
        Dimension ballDimension = new Dimension(AwtDemo.width, ballHeight);
        Dimension racketDimension = new Dimension(AwtDemo.width, AwtDemo.height-ballHeight);
        Demo20BallCanvas ballCanvas = new Demo20BallCanvas(ballDimension,ballRound);
        Demo20RacketCanvas racketCanvas = new Demo20RacketCanvas(racketDimension,racketWidth);
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    location[2] -= 10;
                    if(location[2]<0){
                        location[2] = 0;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    location[2] += 10;
                    if(location[2]+racketWidth>AwtDemo.width){
                        location[2] = AwtDemo.width-racketWidth;
                    }
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    location[2] += 10;
                    if(location[2]+racketWidth>AwtDemo.width){
                        location[2] = AwtDemo.width-racketWidth;
                    }
                }

                racketCanvas.reSet(location[2]);
                racketCanvas.repaint();
            }
        };
        ballCanvas.addKeyListener(keyAdapter);
        racketCanvas.addKeyListener(keyAdapter);
        frame.add(ballCanvas,BorderLayout.CENTER);
        frame.add(racketCanvas,BorderLayout.SOUTH);
        frame.pack();
        timer = new Timer(1, (e) -> {
            if (location[0] > (AwtDemo.width - ballRound) || location[0] < 0) {
                isDown[0] = location[0] < (AwtDemo.width - ballRound);
            }
            if (location[1] > (ballHeight - ballRound) || location[1] < 0) {
                if (location[1] > (ballHeight - ballRound)) {
                    boolean outLeft = location[0] < location[2];
                    boolean outRight = location[0] > location[2] + racketWidth;
                    if (outLeft || outRight) {
                        isOver.set(true);
                        ballCanvas.removeKeyListener(keyAdapter);
                        ballCanvas.removeKeyListener(keyAdapter);
                        timer.stop();
                        System.out.println("最后得分:"+scope);
                    }else{
                        scope.getAndIncrement();
                        isPlay.set(true);
                        System.out.print("击中！");
                        System.out.print("\t小球当前X坐标:"+location[0]);
                        System.out.println("\t球拍当前X坐标:"+location[2]);
                    }
                    System.out.println("当前分数:"+scope);
                }
                isDown[1] = location[1] < (ballHeight - ballRound);
            }
            int offset = 1;
            if(isPlay.get()){
//                offset = (int) Math.abs((Math.random() * 100-50));
                isPlay.set(false);
            }
            location[0] = isDown[0]?location[0]+offset:location[0]-offset;
            location[1] = isDown[1]?location[1]+2:location[1]-2;
            ballCanvas.reSet(location[0], location[1],isOver);
            ballCanvas.repaint();
        });
        timer.start();
    }

    static class Demo19Canvas extends Canvas {
        Demo19Canvas(Dimension dimension) {
            this.setPreferredSize(dimension);
        }

        @Override
        public void paint(Graphics g) {
            if (type == 0) {
                g.setColor(Color.pink);
                g.drawRect(150, 100, 100, 100);
            } else {
                g.setColor(Color.pink);
                g.drawOval(150, 100, 100, 100);
            }

        }
    }

    /**
     * Graphics组件演示
     */
    static void demo19() {
        Frame frame = createBaseWindow("Graphics组件演示");
        Panel panel = new Panel();
        panel.setPreferredSize(new Dimension(400, 400));
        Demo19Canvas myCanvas = new Demo19Canvas(new Dimension(400, 300));
        panel.add(myCanvas);
        frame.add(panel, BorderLayout.CENTER);
        Panel buttons = new Panel();
        String[] text = {"绘制矩形", "绘制圆形"};
        for (String s : text) {
            Button button = new Button(s);
            button.addActionListener(e -> {
                if (e.getActionCommand().equals("绘制矩形")) {
                    type = 0;
                } else if (e.getActionCommand().equals("绘制圆形")) {
                    type = 1;
                }
                myCanvas.repaint();
            });
            buttons.add(button);
        }
        frame.add(buttons, BorderLayout.SOUTH);
        frame.pack();
        frame.repaint();
    }

    /**
     * PopupMenu组件演示
     */
    static void demo18() {
        Frame frame = createBaseWindow("PopupMenu组件演示");
        Panel panel = new Panel();
        PopupMenu popupMenu = new PopupMenu();
        TextArea text = new TextArea("hello world", 10, 50);
        popupMenu.add(new MenuItem("注释"));
        popupMenu.add(new MenuItem("取消注释"));
        popupMenu.add(new MenuItem("复制"));
        popupMenu.add(new MenuItem("保存"));
        panel.add(popupMenu);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // 判断是不是点击的鼠标右键 e.isPopupTrigger() true:是 false:否
                if (e.isPopupTrigger()) {
                    popupMenu.show(panel, e.getX(), e.getY());
                }
            }
        });
        // 设置panel的大小
        panel.setPreferredSize(new Dimension(100, 100));
        frame.add(text, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
    }

    /**
     * Menu组件演示
     */
    static void demo17() {
        Frame frame = createBaseWindow("menu组件演示");
        Box box = Box.createVerticalBox();
        MenuBar menuBar = new MenuBar();
        TextArea text = new TextArea(10, 50);
        Menu file = new Menu("文件");
        Menu edit = new Menu("编辑");

        edit.addActionListener(e -> System.out.println("点击了编辑"));
        edit.add(new MenuItem("自动换行"));
        edit.add(new MenuItem("复制"));
        edit.add(new MenuItem("粘贴"));
        edit.add(new MenuItem("-"));
        Menu menu = new Menu("格式");
        final MenuItem item = new MenuItem("注释", new MenuShortcut(KeyEvent.VK_Q, true));
        item.addActionListener(e -> text.append("点击了:注释\r\n"));
        menu.add(item);
        menu.add(new MenuItem("取消注释"));
        edit.add(menu);
        menuBar.add(file);
        menuBar.add(edit);
        frame.setMenuBar(menuBar);
        box.add(text);
        frame.add(box);
        frame.pack();
    }

    /**
     * 事件机制
     */
    static void demo16() {
        Frame frame = createBaseWindow("dialog组件演示");
        Box box = Box.createVerticalBox();
        TextField text = new TextField(50);
        text.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("鼠标点击");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("鼠标长按");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("鼠标松开");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("鼠标移入");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("鼠标移出");
            }
        });
        text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("键盘按下");
                System.out.println(e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("键盘长按");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("键盘松开");
            }
        });
        text.addTextListener(e -> System.out.println(e.getSource()));
        box.add(text);
        Button show = new Button("显示文字");
        show.addActionListener(e -> {
            frame.setVisible(false);
            box.add(new TextField(50));
            frame.setVisible(true);
        });
        box.add(Box.createVerticalStrut(5));
        box.add(show);
        frame.add(box);
        frame.pack();
    }

    /**
     * window事件机制
     */
    static void demo15() {
        Frame frame = createBaseWindow("window事件机制");
        Box box = Box.createVerticalBox();
        TextField text = new TextField(50);
        box.add(text);
        Button show = new Button("显示文字");
        show.addActionListener(e -> text.setText("hello world"));
        box.add(Box.createVerticalStrut(5));
        box.add(show);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("窗口打开");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("窗口关闭前");
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("windowClosed");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("窗口最小化");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                System.out.println("窗口从最小化恢复");
            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("窗口聚焦");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("窗口失焦");
            }
        });
        frame.add(box);
        frame.pack();
    }

    /**
     * fileDialog组件演示
     */
    static void demo14() {
        Frame frame = createBaseWindow("fileDialog组件演示");
        FileDialog open = new FileDialog(frame, "打开文件", FileDialog.LOAD);
        open.setBounds(500, 500, 100, 100);
        FileDialog save = new FileDialog(frame, "保存文件", FileDialog.SAVE);
        save.setBounds(500, 500, 100, 100);
        Box box = Box.createVerticalBox();
        Button button1 = new Button("打开文件");
        Button button2 = new Button("保存文件");
        button1.addActionListener((e -> {
            // 选择文件之前代码会阻塞在这里
            open.setVisible(true);
            System.err.println(open);
            System.err.println(open.getDirectory());
            System.err.println(open.getFile());
        }));
        button2.addActionListener((e -> {
            save.setVisible(true);
            System.err.println(save);
            System.err.println(save.getDirectory());
            System.err.println(save.getFile());
        }));
        box.add(button1);
        box.add(button2);
        frame.add(box);
        frame.pack();
    }

    /**
     * dialog组件演示
     */
    static void demo13() {
        Frame frame = createBaseWindow("dialog组件演示");
        Dialog dialog1 = new Dialog(frame, "提示1", true);
        dialog1.setBounds(500, 500, 100, 100);
        Dialog dialog2 = new Dialog(frame, "提示2", false);
        dialog2.setBounds(500, 500, 100, 100);
        Box box = Box.createVerticalBox();
        Button button1 = new Button("按钮1");
        Button button2 = new Button("按钮2");
        button1.addActionListener((e -> dialog1.setVisible(true)));
        button2.addActionListener((e -> dialog2.setVisible(true)));
        Button close1 = new Button("关闭");
        Button close2 = new Button("关闭");
        close1.addActionListener((e -> dialog1.setVisible(false)));
        close2.addActionListener((e -> dialog2.setVisible(false)));
        dialog1.add(close1);
        dialog2.add(close2);
        box.add(button1);
        box.add(button2);
        frame.add(box);
        frame.pack();
    }

    /**
     * 基本组件综合应用
     */
    static void demo12() {
        Frame frame = createWindow("基本组件综合应用");
        Box parent = Box.createVerticalBox();
        Box inputAndList = Box.createHorizontalBox();
        Box inputAndCheckBox = Box.createVerticalBox();
        inputAndCheckBox.add(new TextArea());
        Box select = Box.createHorizontalBox();
        Choice selects = new Choice();
        selects.add("红色");
        selects.add("蓝色");
        selects.add("绿色");
        select.add(selects);
        CheckboxGroup garden = new CheckboxGroup();
        select.add(new Checkbox("男", garden, false));
        select.add(new Checkbox("女", garden, false));
        select.add(new Checkbox("测试勾选"));
        inputAndList.add(inputAndCheckBox);
        List list = new List();
        list.add("红色");
        list.add("蓝色");
        list.add("绿色");
        inputAndList.add(list);
        inputAndCheckBox.add(select);
        parent.add(inputAndList);
        Box inputAndButton = Box.createHorizontalBox();
        inputAndButton.add(new TextField(50));
        Button button = new Button("确认");
        inputAndButton.add(button);
        parent.add(inputAndButton);
        frame.add(parent);

        frame.pack();
    }

    /**
     * Box组件-分割线
     */
    static void demo11() {
        Frame frame = createWindow("Box组件");
        Box box = Box.createVerticalBox();
        Box buttons1 = Box.createVerticalBox();
        Box buttons2 = Box.createHorizontalBox();
        for (int i = 0; i < 8; i++) {
            buttons1.add(new Button("第" + i + "个按钮"));
            buttons2.add(new Button("第" + i + "个按钮"));
        }
        box.add(buttons1);
        box.add(Box.createVerticalStrut(10));
        box.add(buttons2);
        frame.add(box);
        frame.pack();
    }

    /**
     * BoxLayout布局
     */
    static void demo10() {
        Frame frame = createWindow("BoxLayout布局");
        Panel buttons = new Panel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        for (int i = 0; i < 5; i++) {
            buttons.add(new Button("第" + i + "个按钮"));
        }
        frame.add(buttons);
        frame.pack();
    }

    /**
     * CardLayout布局-事件监听
     */
    static void demo9() {
        Frame frame = createWindow("CardLayout布局");
        Panel cards = new Panel();
        CardLayout cardLayout = new CardLayout();
        cards.setLayout(cardLayout);
        for (int i = 0; i < 5; i++) {
            cards.add("第" + i + "段文字", new Button("第" + i + "段文字"));
        }
        Panel buttons = new Panel();
        String[] cmds = new String[]{"previous", "next", "first", "last", "three"};
        for (int i = 0; i < 5; i++) {
            Button button = new Button(cmds[i]);
            button.setActionCommand(cmds[i]);
            button.addActionListener((e) -> {
                switch (e.getActionCommand()) {
                    case "previous":
                        cardLayout.previous(cards);
                        break;
                    case "next":
                        cardLayout.next(cards);
                        break;
                    case "first":
                        cardLayout.first(cards);
                        break;
                    case "last":
                        cardLayout.last(cards);
                        break;
                    case "three":
                        cardLayout.show(cards, "第3段文字");
                        break;
                }
            });
            buttons.add(button);
        }
        frame.add(cards, BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);
        frame.pack();
    }

    /**
     * 计算器演示
     */
    static void demo8() {
        char[] chars = new char[]{'+', '-', '*', '/', '.'};
        Frame frame = createWindow("计算器");
        Panel north = new Panel();
        north.add(new TextField(30));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(3, 5, 10, 5));
        for (int i = 0; i < 10; i++) {
            panel.add(new Button(String.valueOf(i)));
        }
        for (char c : chars) {
            panel.add(new Button(String.valueOf(c)));
        }
        frame.add(north, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * GridLayout布局
     */
    static void demo7() {
        char[] chars = new char[]{'+', '-', '*', '/', '.'};
        Frame frame = createWindow("计算器");
        Panel north = new Panel();
        north.add(new TextField(30));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(3, 5, 10, 5));
        for (int i = 0; i < 10; i++) {
            panel.add(new Button(String.valueOf(i)));
        }
        for (char c : chars) {
            panel.add(new Button(String.valueOf(c)));
        }
        frame.add(north, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * BorderLayout布局-空白
     */
    static void demo6() {
        Frame frame = createWindow("第六个测试窗口");
        frame.setLayout(new BorderLayout(30, 10));
        frame.add(new Button("北侧按钮"), BorderLayout.NORTH);
        frame.add(new Button("南侧按钮"), BorderLayout.SOUTH);
        // 如果某个区域没有内容，那么该区域不会空白，而是会被其他区域填满
        Panel panel = new Panel();
        panel.add(new Button("测试"));
        panel.add(new TextField("测试"));
        frame.add(panel);
        frame.pack();
    }

    /**
     * BorderLayout布局
     */
    static void demo5() {
        Frame frame = createWindow("第五个测试窗口");
        // 设置容器的布局管理器为BorderLayout布局
        // BorderLayout布局
        // 参数分别为 [水平间距，垂直间距]
        frame.setLayout(new BorderLayout(30, 10));
        frame.add(new Button("北侧按钮"), BorderLayout.NORTH);
        frame.add(new Button("南侧按钮"), BorderLayout.SOUTH);
        frame.add(new Button("东侧按钮"), BorderLayout.EAST);
        frame.add(new Button("西侧按钮"), BorderLayout.WEST);
        frame.add(new Button("中间按钮"), BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * FlowLayout布局演示
     */
    static void demo4() {
        Frame frame = createWindow("第四个测试窗口");
        // 设置容器的布局管理器为流式布局
        // FlowLayout为流式布局
        // 参数分别为 [对齐方式,水平间距，垂直间距]
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        // 创建一个带出滚动条的容器
        // 若不传参数，则为超出容器时显示滚动条
        // 若传参数ScrollPane.SCROLLBARS_ALWAYS为总是显示滚动条
        for (int i = 1; i <= 100; i++) {
            frame.add(new Button("测试按钮" + i));

        }
        // 设置窗口最佳大小(可能和你想象中的"最佳"不太一样...)
        frame.pack();
    }

    /**
     * 滚动条演示
     */
    static void demo3() {
        Frame frame = createBaseWindow("第三个测试窗口");
        // 创建一个带出滚动条的容器
        // 若不传参数，则为超出容器时显示滚动条
        // 若传参数ScrollPane.SCROLLBARS_ALWAYS为总是显示滚动条
        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scrollPane.add(new TextField("这是测试文字"));
        scrollPane.add(new Button("这是测试按钮"));
        frame.add(scrollPane);
    }

    /**
     * 按钮和输入框演示
     */
    static void demo2() {
        // 创建一个window窗口
        Frame frame = new Frame("第二个测试窗口");
        frame.setBounds(1400, 500, 500, 500);
        // 设置窗口可见性(创建组件时默认为隐藏)
        frame.setVisible(true);
        Panel panel = new Panel();
        // 创建一个文本框，并添加到Panel容器中
        panel.add(new TextField("测试"));
        // 创建一个按钮，并添加到Panel容器中
        // 由于AWT是调用的操作系统行为来创建组件
        // 在windows中中文操作系统默认编码为gbk
        // 所以在运行时需要添加JVM参数:-Dfile.encoding="gbk"
        // 如果不添加会导致中文乱码
        panel.add(new Button("按钮"));
        // 将创建的Panel组件添加到Frame中
        frame.add(panel);
    }

    /**
     * 基础窗口演示
     */
    static void demo1() {
        // 创建一个window窗口
        Frame frame = new Frame("第一个测试窗口");
        // 设置窗口在屏幕上的位置
        frame.setLocation(1400, 500);
        // 设置窗口大小
        frame.setSize(500, 500);
        // 设置窗口可见性(创建组件时默认为隐藏)
        frame.setVisible(true);
    }

    /**
     * 根据x,y,width,height创建基础窗口
     *
     * @return 创建出来的基础窗口
     */
    static Frame createBaseWindow(String title) {
        // 创建窗口
        Frame frame = new Frame(title);
        // 设置位置和宽高
        frame.setBounds(AwtDemo.x, AwtDemo.y, AwtDemo.width, AwtDemo.height);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        // 显示窗口
        frame.setVisible(true);

        return frame;
    }

    /**
     * 创建基础窗口
     *
     * @return 创建出来的基础窗口
     */
    static Frame createWindow(String title) {
        // 创建窗口
        Frame frame = new Frame(title);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        // 显示窗口
        frame.setVisible(true);

        return frame;
    }
}
