# gui_ebs_client


AWT tutorial  =>    https://ru.wikibooks.org/wiki/Java/%D0%9F%D0%B5%D1%80%D0%B2%D0%BE%D0%B5_%D0%BE%D0%BA%D0%BD%D0%BE

positions => http://www.quizful.net/post/swing-layout-managers
layouts => https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html#group
WAV in JAVA tutorial => https://www.youtube.com/watch?v=GVtl19L9GxU
Layouts in SWING => https://www.intuit.ru/studies/courses/16/16/lecture/27125?page=6
Menu in SWING => https://javaswing.wordpress.com/2010/02/20/jmenubar/
Adding key listeners => https://www.codejava.net/java-se/swing/setting-shortcut-key-and-hotkey-for-menu-item-and-button-in-swing
Lambda => https://metanit.com/java/tutorial/9.2.php
KeyStrokes => https://examples.javacodegeeks.com/desktop-java/swing/java-swing-key-binding-example/
Wav description => https://audiocoding.ru/article/2008/05/22/wav-file-structure.html
Video => https://github.com/sarxos/webcam-capture

Games => https://www.youtube.com/watch?v=4ShiPCyukwQ


EBS => https://bankir.ru/dom/forum/%D0%B4%D0%B5%D0%BF%D0%B0%D1%80%D1%82%D0%B0%D0%BC%D0%B5%D0%BD%D1%82-%D1%82%D0%B5%D1%85%D0%BD%D0%BE%D0%BB%D0%BE%D0%B3%D0%B8%D0%B9/%D0%B8%D0%BD%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D0%B0%D1%8F-%D0%B1%D0%B5%D0%B7%D0%BE%D0%BF%D0%B0%D1%81%D0%BD%D0%BE%D1%81%D1%82%D1%8C/4828688-%D0%B5%D0%B4%D0%B8%D0%BD%D0%B0%D1%8F-%D0%B1%D0%B8%D0%BE%D0%BC%D0%B5%D1%82%D1%80%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F-%D1%81%D0%B8%D1%81%D1%82%D0%B5%D0%BC%D0%B0-%D0%B5%D0%B1%D1%81/page28
LINKS TO REQUIREMENTS => http://www.consultant.ru/document/cons_doc_LAW_304253/
отношение сигнал-шум для звука 20-100 дБ;
 глубина квантования 8-32 бит;  =>24 bit;
 частота дискретизации 8-96 кГц;   =>48 kHz;
 длина записи 5-35 секунд;
 количество каналов в записи от 1 (моно режим) до 2 (стерео режим);
 размер проверяемых данных 100-1500 Кб.


playSound (filename. offset1 , offset2 )
more offset1 => more drops from end to begin(flushed ended)
more offset2 => more drop from begin in output play; good for positioning;

DURATION wav => https://stackoverflow.com/questions/2709508/how-to-learn-wav-duration-in-java-media-frame-work/2716637