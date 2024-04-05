import django
from django.db import models


class Creator(models.Model):
    id = models.AutoField(primary_key=True, unique=True)
    login = models.CharField(max_length=64, unique=True)
    password = models.CharField(max_length=128)
    firstname = models.CharField(max_length=64)
    lastname = models.CharField(max_length=64)

    class Meta:
        db_table = 'tbl_creator'

    def __str__(self):
        return "[CREATOR] id:" + str(self.id) + ";login: " + self.login \
               + ";firstname: " + self.firstname + ";lastname: " + self.lastname


class Issue(models.Model):
    id = models.AutoField(primary_key=True)
    creatorId = models.ForeignKey(Creator, on_delete=models.CASCADE)
    title = models.CharField(max_length=64, unique=True)
    content = models.TextField(max_length=2048)
    created = models.DateTimeField(default=django.utils.timezone.now)
    modified = models.DateTimeField(default=django.utils.timezone.now)

    class Meta:
        unique_together = ('id', 'title')
        db_table = "tbl_issue"

    def __str__(self):
        return "[ISSUE] id:" + str(self.id) + ";creatorId:" + str(self.creatorId) + ";title:" + self.title


class Note(models.Model):
    id = models.AutoField(primary_key=True)
    issueId = models.ForeignKey(Issue, on_delete=models.CASCADE)
    content = models.TextField(max_length=2048)

    class Meta:
        db_table = 'tbl_note'

    def __str__(self):
        return "[NOTE] id:" + str(self.id) + ";issueId:" + str(self.issueId)


class Tag(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=32, unique=True)
    issues = models.ManyToManyField(Issue)

    class Meta:
        db_table = 'tbl_tag'

    def __str__(self):
        return "[TAG] id:" + str(self.id) + ";name:" + self.name

