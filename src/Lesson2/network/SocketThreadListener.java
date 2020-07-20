package Lesson2.network;

import java.net.Socket;

public interface SocketThreadListener {

    void onSocketStart(SocketThread thread, Socket socket);
    void onSocketStop(SocketThread thread);
    void onSocketReady(SocketThread thread, Socket socket);
    void onReceiveString(SocketThread thread, Socket socket, String msg);
    void onSocketException(SocketThread thread, Throwable throwable);

}
/*
SocketException, рассказать, когда оно встречается и как его обычно обрабатывают.

Изучил код и понял следующие моменты:
1. Существуют клиенты и сервера, вернее сказать один сервер и множество клиентов.
2. Они общаются посредством СОККЕТОВ.
3. Клиенты для установки подключения требуется адрес сервера и его порт.
4. Серверу требуется лишь порт, для ожидания подключений от клиентов.
5. Клиенты инициируют подключение, сервер принимает или отвергает подключение.
6. Запись и чтение в соккеты производится посредством потоков ввода и вывода.
7. Для передачи более сложных данных чем побайтные запись/чтение используются более сложные потоки ввода/вывода которые наследуются от первичных.
8. Обработка приёма и передачи данных осуществляется в отдельномпотоке программы клиента или сервера.
9. Сервер, на каждое новое подключение создаёт отдельный сокет и передаёт обработку по чтению и записи данных в него отдельному потоку.


Почему в классе SocketThread в методе public synchronized boolean sendMessage используем именно тип boolean?
Для чего нужен метод out.flush() в классе SocketThread в методе sendMessage?
Не совсем понимаю, каким образом нам помогает метод onSocketReady, который вызывается в методе run в классе SocketThread
(несколько раз пересматривал Ваше объяснение в вебинаре, но не могу разобраться, как это работает)?

Поток, который мы создаём в методе onSocketAccepted в классе ChatServer закрывается же в методе run (класс SocketThread)?
Правильно я понял?
Обратил внимание на следующее:
При вводе сообщения и нажатии на клавишу "Send" без нажатия на кнопку "Login" вылетает эксепшн и "падает" ClientGUI.
Если в ClientGUI в JTextField tfPort ввести текст, вместо цифр, то вылетает эксепшн и "падает" ClientGUI.

1. Первый и главный вопрос: почему именно так? Почему такая архитектура и чем это обусловлено? Можно ли реализовать всё иначе и как? Быть может, есть уже отработанные пути решения этой задачи? Черезчур обширный вопрос, но он возник практически сразу и не покидает меня до сих пор.
2. При работе с асинхронностью резонно возникает вопрос, как обрабатывать случаи, когда клиент уже завершает работу, скажем, при помощи interrupt, но другие клиенты и сервер продолжают с ним активно обмениваться данными? Он постепенно завершает работу и после этого просто отваливается из обмена или следует вводить какие-то флаги состояний, которые выбрасываются в момент начала interrupt, чтобы оповестить других, что слать ему уже ничего не нужно?
3. Как я понимаю, клиент и сервер реализуют интерфейс SocketThreadListener, который позволяет им строить своё поведение в одной парадигме. Но мне пока сложно понять, как именно взаимодействуют ServerSocketThread и SocketThread c самим ChatServer? Моё понимание интерфейсов пока находится на уровне "классы реализуют интерфейсы". В ServerSocketThread и SocketThread же я вижу лишь наследование от класса Thread и создание некой ссылки на интерфейсы SocketThreadListener и ServerSocketThreadListener с последующим использованием методов, описанных в интерфейсе. Почему это реализованно именно так, на не через привычное "классы реализуют интерфейсы"?

Не понял до конца, как у нас происходит обмен данными между обьектами SocketThread (серверным и клиентским)? Где связь между клиентским сокетом и серверным?
Может ли клиентский сокет остановить поток серверного сокета? Или он останавливается автоматически при остановке клиентского сокета?
Как в таких приложения лучше делать логирование?

Вопрос по дисконнескту. Вроде как работает, но вместо того чтобы просто расконнектиться, выдает эксепшн

1. Непонятно как работает метод timeOut() и какова его роль в потоке.
2. Каким образом закрывается поток и существует ли сокет после закрытия потока или нет.
3. Как взаимодействуют треды, интерфейсы и сервер.

не понятен смысл метода onServerTimeout. В каком случае его отсутствие критично повлияет на работоспобность чата?

Мы не доконца реализовали метод "stop" для сервера, так как я понимаю при остановке сервера он лишь перестает принимать новые сокеты, но связь со старыми продолжает держать.
Надо как-то убивать текущие сокеты ?

Не совсем понятем метод TimeOut.
Можно нарисовать прям схему со всеми компонентами?
Имеется ввиду есть ЧатСервер у него СерверГуи,
Чатсервер обращается СерверСокедТреду через интерфейс и так далее.
То же самое оносительно клиента.

Что будет если к ServerSocketThread одновременно будут подключаться несколько клиентов? server.accept() поставит их в какую то очередь?
Почему socketThread.sendMessage(msg) возвращает boolean? На клиенте он используется как void
Что такое out.flush()?
Более масштабные вопросы сложно сформулировать, к сожалению, пока не улеглось все в голове. Все кажется понятным, но полностью осмыслись сложно, пока не хватило времени.

У меня есть понимание, для чего нужны ServerSocket, Socket, Thread.
И нет понимания как работает сеть. Нужно времени побольше разобраться в этом во всём, поэтому не могу задать адекватных вопросов.
Слушатели сокетов, работа сокетов и synchronized - для меня пока что какая-то дичь.

1. Первый вопрос можно визуально изобразить как SocketThread и SocketThreadListener взаимодействую с ChatServer. Пример (рисунок) который показали на уроке взаимодействия ServerSocketThread и ServerSocketThreadListener был очень понятен.
2. Зачем мы используем private final int timeout в ServerSocketThread, что происходит в server.setSoTimeout(timeout); для чего нужно?
3. Что происходит в методе sendMessage в моменте out.writeUTF(msg); out.flush(); что делает out.flush()?
4 Правильно я понимаю ,что listener.onSocketAccepted(this, server, s); из 2-х полусокетов в этот момент образуется соккет?

1.как работает метод server.setSoTimeout(timeout); (строка 26)
что произойдет, если некоторое количество потоков, одновременно к нему обратятся?
2. чем отличается out.flush() (стока 43) От out.close()(строка 54)
3. synchronized void close (стока 52) -  в случае с sendMessage, там понятно, а вот здесь зачем synchronized не совсем понял?

1. По коду вопросов нет, для меня очень сложная архитектура
2. не понял взаимодействия Сервер ГУИ и Чат-Сервер, почему нельзя все в одном классе, просмотрел урок 2-ды, буду еще смотреть сегодня, также смотрел более простую версия написания чата от Алексея Степченко от 2017 года на Ютубе.
3. Вообще сложность составила только Архитектура, нереально тяжело в ней разобраться.

Класс Socket Thread, метод sendMessage. В методе мы запускаем новый поток. Но почему тогда без run()? Потому что в методе DataOutputStream?
Один большой вопрос по теме Exception. Не всегда понимаю гед надо проверять на exception, где надо использовать finally
Метод sendMessage работает с серваком и клиентом и мы его синхронизируем, но в методе run есть in.readUTF и он тоже с серваком и клиентом. И сообщения могут сыпаться от 1000 клиентов одновременно.Почему тут нет синхронизации?
1. Полтора дня боролся с кодом, чтобы он начал работать. Мне удобнее это делать с нуля самому, потому что более понятно становится. Вопрос, с Вашей точки зрения стоит это делать самому или все таки лучше скопипастить?
2. Не очень понял зачем нам нужен timeout.
3. В целом плюс минус все понятно, было бы круто, если бы еще раз обрисовали схематично что и как работает.

Из непонятого только то как клиент узнает IP на который нужно слать сообщения.

1 как закрыть корректно поток ввода, при закрытии сокета? в простых приложениях, проблем с закрытием потока не было, везде предлагается вариант перехвата и подавления EOFException, но в домашке так не работает.
2 Вопрос по архитектуре - по методу onReceiveString - у меня получается, когда рассылаем сообщения всем сокетам, сохраненным в векторе, аргументы метода (кроме строки) не используются.
3 Можно ли создать несколько перегруженных методов в интерфейсе SocketThreadListener?

 try catch с ресурсами, как и когда его использовать и в чем приемущества

Как обрабатывать исключения, возникающие на сервере при отключении клиента? (при реализации кнопки disconnect например).

1. Не очень понятно, как работает TimeOut (зачем он нужен понятно, а именно как он работает не очень)
2. А вы нам расскажете, как организовать подключение к IPv6? То есть получается, что клиенты с таким IP не смогут подключиться к серверу? Таких ip сейчас много, и всё больше?
3. Что будет, если повредится пакет при передаче?
4. Не очень поняла, зачем в интерфейсе нужен этот метод:
void onReceiveString(SocketThread thread, Socket socket, String msg);
5. Непонятно, какие нужны методы в ServerSocketListner. С точки зрения архитектуры. Можно, если не сложно, показать на такой же схеме как на прошлом уроке была схема для ServerSocketThreadListner, как он взаимодействует с другими классами?

* */