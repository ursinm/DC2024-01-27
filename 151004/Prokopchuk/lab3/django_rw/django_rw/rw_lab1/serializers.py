from rest_framework import serializers
from .models import Creator, Issue, Note, Tag


class CreatorSerializer(serializers.ModelSerializer):

    login = serializers.CharField(min_length=2, max_length=64)
    password = serializers.CharField(min_length=8, max_length=64)
    firstname = serializers.CharField(min_length=2, max_length=64)
    lastname = serializers.CharField(min_length=2, max_length=64)

    class Meta:
        model = Creator
        fields = '__all__'


class IssueSerializer(serializers.ModelSerializer):

    creatorId = serializers.PrimaryKeyRelatedField(queryset=Creator.objects.all())
    title = serializers.CharField(min_length=2, max_length=64)
    content = serializers.CharField(min_length=4, max_length=2048)

    class Meta:
        model = Issue
        fields = ['id', 'creatorId', 'title', 'content']


class NoteSerializer(serializers.ModelSerializer):

    issueId = serializers.PrimaryKeyRelatedField(queryset=Issue.objects.all())
    content = serializers.CharField(min_length=2, max_length=2048)

    class Meta:
        model = Note
        fields = ['id', 'issueId', 'content']


class TagSerializer(serializers.ModelSerializer):

    name = serializers.CharField(min_length=2, max_length=32)

    class Meta:
        model = Tag
        fields = ['id', 'name']

